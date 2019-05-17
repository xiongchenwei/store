package com.xiaoxiong.service;

import com.alibaba.fastjson.JSONObject;
import com.xiaoxiong.adapter.MassageAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 适配器设计模式（重构，一种设计思想）
 */

@Service
@Slf4j
public class MailboxService implements MassageAdapter {

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void distribute(JSONObject jsonObject) {

        JSONObject content = jsonObject.getJSONObject("content");
        String mail = content.getString("mail");
        String userName = content.getString("userName");
        log.info("###消息者收到消息###mail:{},userName:{}", mail, userName);

        // 发送邮件
        // 开始发送邮件
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mail);
        message.setTo(mail); // 自己给自己发送邮件  ，网哪个邮箱发送
        message.setSubject("小熊微信商城会员注册成功");
        message.setText("恭喜您" + userName + ",成为小熊微信商城的新用户!谢谢您的光临!");
        log.info("###发送短信邮箱 mail:{}", mail);
        mailSender.send(message);

    }
}
