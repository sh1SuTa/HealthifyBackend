package com.putileaf.healthify.controller;

import com.putileaf.healthify.entity.Result;
import com.putileaf.healthify.entity.User;
import com.putileaf.healthify.service.MailCodeService;
import com.putileaf.healthify.service.UserService;
import com.putileaf.healthify.utils.JwtUtil;
import com.putileaf.healthify.utils.Md5Util;
import com.putileaf.healthify.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")

//数据校验
@Validated

public class UserController {

    @Autowired
    private UserService userService;
    @Autowired private StringRedisTemplate stringRedisTemplate;
    @Autowired private MailCodeService mailCodeService;
    @PostMapping("/register")
    //判断是否符合用户名规则
    public Result<String> register(@Pattern(regexp="^\\S{5,16}$")String username,@Pattern(regexp="^\\S{5,16}$")String password){
            //查询用户
            User u = userService.findByUsername(username);
            if (u==null){//可以注册
                userService.register(username,password);
                return Result.success("注册成功");
            }else {//用户名已存在不能注册
                return Result.error("用户名被注册");
            }
    }

    //登录逻辑
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp="^\\S{5,16}$")String username,@Pattern(regexp="^\\S{5,16}$")String password){
        //根据用户名查询用户
        User loginUser = userService.findByUsername(username);
        //判断用户是否存在
        if (loginUser==null){
            return Result.error("用户不存在");
        }
        //判断密码是否正确
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            Map<String,Object> claims = new HashMap<>();
            //给Map集合加id和username字段
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            claims.put("level",loginUser.getLevel());
            //生成JWT令牌
            String token = JwtUtil.genToken(claims);
            //把token存储在redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.HOURS);
            //返回JWT令牌
            return Result.success("登录成功",token);
        }
        return Result.error("密码错误");
    }


    //根据用户名查询用户
    @GetMapping("/userInfo")
    public Result<User> userinfo(HttpServletRequest request){
        try {
            // 打印或处理 authorization
            String token = request.getHeader("Authorization");
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
            Map<String,Object> map = ThreadLocalUtil.get();
            String username =(String) map.get("username");
            User user = userService.findByUsername(username);
            return Result.success("查询成功",user);
        }catch (IllegalArgumentException e){
            return Result.success("未登录",null);
        }
    }

    //更新
    @PutMapping("/update")
    //Validated让传入的参数进行对象变量校验
    public Result<String> update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success("修改成功");
    }

    //修改头像
    @PatchMapping("/updateAvatar")
    public Result<String> updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success("修改头像成功");
    }


    //修改密码
    @PatchMapping("/updatePwd")
    public Result<String> updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token){
        //校验参数
        String oldPwd = params.get("old_pwd");//旧密码
        String newPwd = params.get("new_pwd");//新密码
        String rePwd = params.get("re_pwd");//确认新密码
        if ( (!StringUtils.hasLength(oldPwd)) || (!StringUtils.hasLength(newPwd)) || (!StringUtils.hasLength(rePwd)) ){
            //只要有一个值是空的，直接拒绝处理
            return Result.error("请填写密码");
        }
        //先查询原账号的密码
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUsername(username);
        //将查询到的密码跟用户输入的旧密码比对
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            //如果密码不一致
            return Result.error("原密码不正确");
        }
        //判断新密码和确认新密码是否一致
        if (!rePwd.equals(newPwd)){
            return Result.error("两次填写的新密码不一致");
        }
        //全部判断结束，开始重置密码
        userService.updatePwd(newPwd);
        //删除redis中的旧token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success("修改成功");
    }

    //忘记密码raw-json
    // {
    //    "username": "2903039102",
    //    "code": "2075",
    //    "newPwd": "123456"
    //}
    @PatchMapping("/forgetPwd")
    public Result<String> forgetPwd(@RequestBody Map<String,String> params){
        String username = params.get("username");
        if (!StringUtils.hasLength(username)){
            return Result.error("请填写用户名");
        }
        if (!mailCodeService.checkCode(username,params.get("code"))){
            return Result.error("验证码错误");
        }
        //获取要重置的密码
        String newPwd = params.get("newPwd");
        if (!StringUtils.hasLength(newPwd)){
            return Result.error("请填写新密码");
        }
        //判断密码是否符合规则
        if (!newPwd.matches("^\\S{5,16}$")){
            return Result.error("密码长度必须在5-16位");
        }
        userService.forgetPwd(username,newPwd);
        return Result.success("重置密码成功");
    }

    //获取重置密码的验证码
    @GetMapping("/getCode")
    public Result<String> getCode(@RequestParam("username") String username){
        //查询这个用户的邮箱
        String userMail = userService.findByUsername(username).getEmail();
        //判断验证码是否超过60秒
        if(mailCodeService.codeIsHave(userMail)){
            return Result.error("验证码不可以重复发送，请稍后重试");
        }
        //判断用户名是否为空
        if (!StringUtils.hasLength(username)){
            return Result.error("请填写用户名");
        }

        //如果邮箱为空
        if (!StringUtils.hasLength(userMail)){
            return Result.error("这个账号没有绑定邮箱，请联系管理员");
        }
        //发送邮件
        mailCodeService.sendCodeMail(username, userMail);
        return Result.success("发送成功");
    }








}
