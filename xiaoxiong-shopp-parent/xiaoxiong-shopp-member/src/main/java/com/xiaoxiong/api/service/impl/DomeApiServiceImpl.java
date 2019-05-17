package com.xiaoxiong.api.service.impl;

import com.xiaoxiong.api.service.DomeApiService;
import com.xiaoxiong.common.api.BaseApiService;
import com.xiaoxiong.common.redis.BaseRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class DomeApiServiceImpl extends BaseApiService implements DomeApiService {

    @Autowired
    BaseRedisService baseRedisService;

    @Override
    public Map<String, Object> Dome() {

        baseRedisService.setString("name", "张三");

        log.info(String.format("这是日志的输出[%s]","你好哈"));

        return setResutSuccess();
    }

    @Override
    public Map<String, Object> DomeRedis(){

        String val = baseRedisService.get("name");

        return setResutSuccessData(val);
    }
}
