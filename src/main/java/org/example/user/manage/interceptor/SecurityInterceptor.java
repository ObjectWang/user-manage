package org.example.user.manage.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.example.user.manage.common.exception.BusinessException;
import org.example.user.manage.common.exception.ExceptionEnum;
import org.example.user.manage.common.utils.RedisCache;
import org.example.user.manage.common.utils.UserHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        // 1.获取uri
        String uri = request.getRequestURI();

        // 2.获取用户权限
        List<String> permissions = UserHolder.getUser().getPermissions();

        // 3.没有权限则设置状态码并拦截
        if (CollectionUtils.isEmpty(permissions) || !permissions.contains(uri)) {
            response.setStatus(403);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String message = "授权失败";
            response.getWriter().write("{\"error\":\"" + message + "\"}");
            return false;
        }

        // 4.放行
        return true;
    }


    public static void main(String[] args) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        System.out.println(antPathMatcher.match("/manage/user/login/**", "/manage/user/login/1"));
    }
}
