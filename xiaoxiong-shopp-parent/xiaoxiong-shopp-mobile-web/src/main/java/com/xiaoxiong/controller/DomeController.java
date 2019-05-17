package com.xiaoxiong.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class DomeController {

    //index页面
    public static final String INDEX = "index";

    @RequestMapping("/index")
    public  String index() {
        log.info("搭建了web的工程！");
        return INDEX;
    }
}
