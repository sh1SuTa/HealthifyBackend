package com.putileaf.healthify.service.impl;

import com.putileaf.healthify.entity.User;
import com.putileaf.healthify.mapper.UserMapper;
import com.putileaf.healthify.service.UserService;
import com.putileaf.healthify.utils.Md5Util;
import com.putileaf.healthify.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    //查找用户名
    @Override
    public User findByUsername(String username) {
        User u = userMapper.findByUsername(username);
        return u;
    }

    //注册
    @Override
    public void register(String username, String password) {
        //加密密码
        String md5String = Md5Util.getMD5String(password);
        //传给mapper层
        userMapper.add(username,md5String);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id =(Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    //登录用户重置密码
    @Override
    public void updatePwd(String newPwd) {
        //先对密码进行加密
        String md5String = Md5Util.getMD5String(newPwd);
        //获取用户id
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id =(Integer) map.get("id");
        //传给mapper
        userMapper.updatePwd(md5String,id);
    }

    //忘记密码重置
    @Override
    public void forgetPwd(String username, String newPwd) {
        //获取用户id
        User user = userMapper.findByUsername(username);
        userMapper.updatePwd(Md5Util.getMD5String(newPwd),user.getId());
    }


}
