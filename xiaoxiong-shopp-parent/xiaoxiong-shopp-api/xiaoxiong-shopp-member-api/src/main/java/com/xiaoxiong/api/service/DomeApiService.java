package com.xiaoxiong.api.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("dome")
public interface DomeApiService {

    @GetMapping("/domeApi")
    public Map<String,Object> Dome();

    @GetMapping("/domeRedis")
    public Map<String, Object> DomeRedis();
}
