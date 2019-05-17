package com.xiaoxiong.mq;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * 功能描述:(往消息服务 推送 邮件信息)
 */

@Service
public class RegisterMailboxProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void send(Destination destination, String json) {

        jmsMessagingTemplate.convertAndSend(destination, json);

    }
}
