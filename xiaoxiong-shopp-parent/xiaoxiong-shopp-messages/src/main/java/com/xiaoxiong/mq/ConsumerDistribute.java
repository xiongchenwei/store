package com.xiaoxiong.mq;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaoxiong.adapter.MassageAdapter;
import com.xiaoxiong.common.constants.MQInterfaceType;
import com.xiaoxiong.service.MailboxService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 *
 * (消费消息，从队列中  获取最新消息)
 *
 */
@Slf4j
@Component
public class ConsumerDistribute {

    @Autowired
    private MailboxService mailboxService;

    @JmsListener(destination = "mail_queue")
    public void distribute(String json) {

        try {
            log.info("###消息服务###收到消息，消息内容json：{}",json);
            if (StringUtils.isBlank(json)) {
                return;
            }

            JSONObject jsonObject = JSONObject.parseObject(json);
            JSONObject header = jsonObject.getJSONObject("header");
            String interfaceType = header.getString("interfaceType");

            MassageAdapter massageAdapter = null;
            switch (interfaceType) {
                //发送邮件
                case MQInterfaceType.SMS_MAIL: massageAdapter = mailboxService;
                    break;

                 default:
                     break;
            }
            massageAdapter.distribute(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
