package com.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author xiaogu
 * @date 2020/7/27 12:23
 **/
@RestController
public class HiController {
    @Autowired
    HiService hiService;
    @Autowired
    FeignConsumer feignConsumer;
    @Autowired
    FeignCourse feignCourse;
    @GetMapping(value = "/hello")
    public String sayHi(String msg){
        return hiService.sayHi(msg);
    }
    @GetMapping(value = "/feign/{msg}")
    public String helloFeign(@PathVariable("msg") String msg) {
        return feignConsumer.hello(msg);
    }
    @GetMapping(value = "courselist")
    public Map courselist(){return feignCourse.listroombyrpc();}
}
