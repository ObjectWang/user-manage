package org.example.user.manage.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.user.manage.common.utils.RedisCache;
import org.example.user.manage.common.utils.UserHolder;
import org.example.user.manage.domain.LoginUser;
import org.example.user.manage.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.example.user.manage.common.constant.RedisConstant.LOGIN_USER_KEY;
import static org.example.user.manage.common.constant.RedisConstant.LOGIN_USER_TTL;

@Slf4j
@Component
@WebFilter(urlPatterns = "/*")
public class AuthorizeFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        //1.判断是否在白名单
        if (isInWhiteList(requestURI)) {
            // 放行
            filterChain.doFilter(request, response);
        }

        // 2.获取请求头中的token
        String token = request.getHeader("authorization");

        // 3.判断token是否存在
        if (StringUtils.isBlank(token)) {
            log.error("未获取到token {}", requestURI);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String message = "认证失败";
            response.getWriter().write("{\"error\":\"" + message + "\"}");
            return;
        }

        // 4.基于TOKEN获取redis中的用户
        String key = LOGIN_USER_KEY + token;
        LoginUser loginUser = redisCache.getCacheObject(key);

        // 5.判断用户是否存在
        if (Objects.isNull(loginUser)) {
            log.error("token过期 {}", requestURI);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String message = "认证失败";
            response.getWriter().write("{\"error\":\"" + message + "\"}");
            return;
        }

        // 6.存在，保存到userHolder
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(loginUser.getUserId());
        userInfo.setUserName(loginUser.getUserName());
        userInfo.setNickName(loginUser.getNickName());
        userInfo.setToken(token);
        userInfo.setPermissions(loginUser.getPermissions());
        UserHolder.saveUser(userInfo);

        // 7.刷新token有效期
        redisCache.expire(key, LOGIN_USER_TTL, TimeUnit.MINUTES);

        try {
            // 8.放行
            filterChain.doFilter(request, response);
        } finally {
            // 9.清除userHolder
            UserHolder.removeUser();
        }
    }

    /**
     * 是否在白名单
     *
     * @param url
     * @return
     */
    private boolean isInWhiteList(String url) {
        List<String> whiteListUrls = new ArrayList<>();
        whiteListUrls.add("/manage/user/login");
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String pattern : whiteListUrls) {
            if (antPathMatcher.match(pattern, url)) {
                return true;
            }
        }
        return false;
    }
}
