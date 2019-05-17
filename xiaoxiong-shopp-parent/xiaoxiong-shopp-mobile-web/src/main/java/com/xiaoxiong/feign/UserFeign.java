
package com.xiaoxiong.feign;

import java.util.Map;

import com.xiaoxiong.entity.UserEntity;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xiaoxiong.api.service.UserService;

@FeignClient("member")
public interface UserFeign {
    /**
     * @methodDesc: 功能描述:(使用token查找用户信息)
     * @param: @param
     * token
     * @param: @return
     */
    @PostMapping("/memberSer/getUser")
    public Map<String, Object> getUser(@RequestParam("token") String token);



    /**
     * 功能描述:登录成功后,生成对应的token，作为key,将用户userID作为value存放在redis中.返回token给客户端.
     *
     * @methodDesc: 功能描述:(登录)
     * @param: @return
     */
    @PostMapping("/memberSer/login")
    public Map<String, Object> login(@RequestBody UserEntity userEntity);

    /**
     * 调用注册服务
     * @param userEntity
     * @return
     */
    @PostMapping("/memberSer/regist")

    public Map<String, Object> regist(UserEntity userEntity);


}
