package com.putileaf.healthify.service;

import com.putileaf.healthify.entity.User;

public interface UserService {
    //根据用户名查询用户
    User findByUsername(String username);

    //注册
    void register(String username, String password);

    void update(User user);

    //更新头像
    void updateAvatar(String avatarUrl);

    //修改密码
    void updatePwd(String newPwd);

    void forgetPwd(String username,String newPwd);
}
