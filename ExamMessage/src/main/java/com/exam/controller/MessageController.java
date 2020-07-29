package com.exam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaogu
 * @date 2020/7/29 02:08
 **/
@RestController
public class MessageController {
    @GetMapping("/")
    public String rootcourse(){
        //逻辑
        return "消息服务";
    }
}
