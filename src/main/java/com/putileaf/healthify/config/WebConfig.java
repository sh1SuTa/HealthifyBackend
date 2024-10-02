package com.putileaf.healthify.config;

import com.putileaf.healthify.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    private LoginInterceptor loginInterceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 指定需要拦截的接口
        String[] needInterceptPaths = {"/dose/add", "/user/info", "/order/**"};
        registry.addInterceptor(loginInterceptors)
                .addPathPatterns(needInterceptPaths);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 配置允许所有路径
                .allowedOrigins( "http://putileaf.top","http://localhost:5173") // 允许特定的源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH") // 允许的HTTP方法
                .allowedHeaders("*") // 允许所有的头部
                .allowCredentials(true) // 允许携带凭证
                .maxAge(3600); // 预检请求的有效期，单位秒

    }

}
