package com.xiaoxiong.api.service;

import com.xiaoxiong.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RequestMapping("/memberSer")
public interface UserService {

    /**
     * 注册服务
     * @param userEntity
     * @return
     */
    @PostMapping("/regist")
    public Map<String, Object> regist(@RequestBody UserEntity userEntity);



    /**
     * 功能描述:登录成功后,生成对应的token，作为key,将用户userID作为value存放在redis中.返回token给客户端.
     *
     * @methodDesc: 功能描述:(登录)
     * @param: @return
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody UserEntity userEntity);

    /**
     *
     * @methodDesc: 功能描述:(使用token查找用户信息)
     * @param: @param
     *             token
     * @param: @return
     */
    @PostMapping("/getUser")
    public Map<String, Object> getUser(@RequestParam(required =false, defaultValue ="4b211b31-28f3-4691-9411-39bb1c950c24" ) String token);
}

