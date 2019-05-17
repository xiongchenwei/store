package com.xiaoxiong.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xiaoxiong.common.api.BaseApiService;
import com.xiaoxiong.common.constants.Constants;
import com.xiaoxiong.common.constants.MQInterfaceType;
import com.xiaoxiong.common.redis.BaseRedisService;
import com.xiaoxiong.common.token.TokenUtils;
import com.xiaoxiong.common.utils.MD5Util;
import com.xiaoxiong.dao.UserDao;
import com.xiaoxiong.api.service.UserService;
import com.xiaoxiong.common.constants.DBTableName;
import com.xiaoxiong.common.utils.DateUtils;
import com.xiaoxiong.entity.UserEntity;
import com.xiaoxiong.mq.RegisterMailboxProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import java.util.Map;

@RestController
@Slf4j
public class UserServiceImpl extends BaseApiService implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private BaseRedisService baseRedisService;

    @Autowired
   private RegisterMailboxProducer registerMailboxProducer;

    //@Value("${messages.queue}")
    private String messages_queue = "mail_queue";


    //用户注册
    @Override
    public Map<String, Object> regist(@RequestBody UserEntity userEntity) {
        if (StringUtils.isBlank(userEntity.getPassword())) {
            return setResutParameterError("用户密码不能为空！");
        }
        if (StringUtils.isBlank(userEntity.getUserName())) {
            return setResutParameterError("用户名称不能为空！");
        }
        if (StringUtils.isBlank(userEntity.getEmail())) {
            return setResutParameterError("用户邮箱不能为空！");
        }
        if (StringUtils.isBlank(userEntity.getPhone())) {
            return setResutParameterError("用户手机号不能为空！");
        }

        try {
            userEntity.setCreated(DateUtils.getTimestamp());
            userEntity.setUpdated(DateUtils.getTimestamp());
            String password = userEntity.getPassword();
            String MD5Password = MD5Util.MD5(password);
            userEntity.setPassword(MD5Password);
            userDao.save(userEntity, DBTableName.TABLE_MB_USER);
            //队列
            Destination activeMQQueue = new ActiveMQQueue(messages_queue);
            //组装报文格式
            String mailMessage = mailMessage(userEntity.getEmail(), userEntity.getUserName());
            log.info("###regist() 注册发送邮件报文mailMessage:{}", mailMessage);
            //mq
            registerMailboxProducer.send(activeMQQueue,mailMessage);

            return setResutSuccess();
        } catch (Exception e) {
            log.error("###regist()  error:", e);
            return setResutError("注册失败！！");
        }
    }


    //用户登录
    @Override
    public Map<String, Object> login(@RequestBody UserEntity userEntity) {

        //往数据库中查数据
        String passw = userEntity.getPassword();
        String password = MD5Util.MD5(passw);
        UserEntity user = null;
        try {
            user = userDao.getUserNameAndPwd(userEntity.getUserName(), password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user==null) {
            return setResutError("账号或密码错误！");
        }
        Long id = user.getId();
        //生成对应的token
        String token = tokenUtils.getToken();
        //将用户的ID作为redis的value，生成的token作为key
        baseRedisService.set(token, id.toString(), Constants.USER_TOKEN_TERMVALIDITY);
        //返回token，给调用端
        return setResutSuccessData(token);
    }

    @Override
    public Map<String, Object> getUser(@RequestParam(required =false, defaultValue ="1aca82c5-a4e8-4ce9-a261-1cb5acca30aa")String token) {

        if (StringUtils.isBlank(token)) {
            return setResutError("token不能为空！");
        }
        String val = baseRedisService.get(token);
        if (StringUtils.isBlank(val)) {
            return setResutError("用户已过期！");
        }
        Long id = Long.parseLong(val);
        UserEntity userEntity = userDao.getUserEntity(id);
        userEntity.setPassword(null);
        return setResutSuccessData(userEntity);
    }

    private String mailMessage(String email, String userName) {
        JSONObject root = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("interfaceType", MQInterfaceType.SMS_MAIL);
        JSONObject content = new JSONObject();
        content.put("email", email);
        content.put("userName", userName);
        root.put("header", header);
        root.put("content", content);
        return root.toJSONString();
    }
}
