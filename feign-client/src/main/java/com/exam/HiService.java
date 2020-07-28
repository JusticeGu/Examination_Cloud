package com.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaogu
 * @date 2020/7/27 12:20
 **/
@Service
public class HiService {
    @Autowired
    FeignConsumer feignConsumer;

    public String sayHi(String name){
        return feignConsumer.hello(name);
    }
}
