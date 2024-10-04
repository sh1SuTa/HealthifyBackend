package com.putileaf.healthify.interceptors;

import com.putileaf.healthify.utils.JwtUtil;
import com.putileaf.healthify.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;



import java.util.Map;
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /** {@code HttpServletRequest request}
     *描述：表示当前 HTTP 请求的信息，包括请求头、请求参数、请求方法等。
      *  用途：可以从中获取请求参数、请求头、请求方法等信息。

     <p>{@code HttpServletResponse response}
        *描述：表示当前 HTTP 响应的信息，可以用来设置响应头、响应状态码、响应体等。
        *用途：可以用来设置响应头、重定向、发送响应数据等。
     * <p>{@code @NonNull} 代表response不为空，{@code  @Nullable} 代表handler允许为空
     * <p>
     *   对于复杂请求POST，需要预处理OPTIONS请求，否则会报错</p>
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @Nullable Object handler) {
        // 检查请求方法是否为 OPTIONS
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
            response.setHeader("Access-Control-Allow-Headers", "authorization, content-type");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Max-Age", "3600");
            return true;
        }
        //获取令牌
        String token = request.getHeader("Authorization");

        //验证token
        try {
            //从redis获取token
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            //能找到token
            String redisToken = operations.get(token);
            //找不到token
            if (redisToken==null){
                //token失效
                throw new RuntimeException();
            }
            //找到的token解析出来
            Map<String, Object> claims = JwtUtil.parseToken(token);
            //把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            //已登录放行
            return true;
        } catch (Exception e) {
            //http响应码401
            response.setStatus(401);
            //未登录不给予放行，也不一定是未登录
            return false;
        }
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,@Nullable Object handler, Exception ex){
        //清空ThreadLocal中的token数据
        ThreadLocalUtil.remove();
    }






}
