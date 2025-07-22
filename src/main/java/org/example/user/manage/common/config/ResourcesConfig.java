package org.example.user.manage.common.config;


import org.example.user.manage.common.utils.RedisCache;
import org.example.user.manage.interceptor.SecurityInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class ResourcesConfig implements WebMvcConfigurer {

    @Resource
    private RedisCache redisCache;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //权限拦截器
        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login",
                        "/user/logout",
                        "/error"
                ).order(0);
    }
}
