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
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 指定需要排除的路径
        String[] excludePaths = {"/user/login", "/user/userInfo", "/drugs/list", "/drugs/list/**","/forum/post", "/forum/post/**"};

        // 添加拦截器，并排除指定的路径
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns(excludePaths);  // 排除指定的路径
    }

    // 配置跨域CORS
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
