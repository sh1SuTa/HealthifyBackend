package com.putileaf.healthify.service.impl;

import com.putileaf.healthify.service.MailCodeService;
import com.putileaf.healthify.utils.FourCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.util.concurrent.TimeUnit;

@Service
public class MailCodeServiceImpl implements MailCodeService {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private JavaMailSender sender;


    @Override
    //判断验证码是否存在redis中
    public boolean codeIsHave(String email) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(email));
    }
    @Override
    public void sendCodeMail(String username, String email) {
        //生成一个四位数验证码
        String code = FourCode.getCode();
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        //将验证码存入redis，设置验证码有效期五分钟
        operations.set(username,code,5, TimeUnit.MINUTES);
        //设置验证码发送冷却60秒
        operations.set(email,code,50, TimeUnit.SECONDS);

        SimpleMailMessage mail = new SimpleMailMessage();
        //设置邮件标题
        String subject = "putiLeaf验证码";
        mail.setSubject(subject);
        //邮件内容
        String content = "\n" +
                "您好！\n" +
                "您正在申请发送验证码：\n" +
                "有效时间：5分钟\n" +
                "为了账号安全，请在指定位置输入下列验证码： "+code +
                "\n如果本次请求并非由您发起，请务必告知我们, 由此给您带来的不便敬请谅解。";
        mail.setText("尊敬的用户"+username+ content);
        mail.setTo(email);
        String from = "putileaf@foxmail.com";
        mail.setFrom(from);
        //调用方法发送邮件
        sender.send(mail);
    }

    @Override
    public boolean checkCode(String username, String code) {
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(username))){
            String redisCode = stringRedisTemplate.opsForValue().get(username);
            if (redisCode != null && redisCode.equals(code)){
                stringRedisTemplate.delete(username);
                return true;
            }
        }

        return false;
    }
}
