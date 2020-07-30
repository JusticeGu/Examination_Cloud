package com.exam.service.impl;

import com.exam.service.UserServiceFeign;
import org.springframework.stereotype.Component;

/**
 * @author xiaogu
 * @date 2020/7/29 14:40
 **/
@Component
public class UserServiceFeignFallback implements UserServiceFeign {
    @Override
    public String aufeign(){
        return "熔断机制启动";
    }
}
