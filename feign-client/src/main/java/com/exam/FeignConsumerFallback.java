package com.exam;

import org.springframework.stereotype.Component;

/**
 * @author xiaogu
 * @date 2020/7/27 12:27
 **/
@Component
public class FeignConsumerFallback implements FeignConsumer {
    @Override
    public String hello(String msg) {
        return "启动熔断机制";
    }
}
