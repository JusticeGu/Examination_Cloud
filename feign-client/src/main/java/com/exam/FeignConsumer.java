package com.exam;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiaogu
 * @date 2020/7/27 12:11
 **/

@FeignClient(value = "povideri",configuration = FeignConfig.class,fallback = FeignConsumerFallback.class)
public interface FeignConsumer {

    @RequestMapping(value = "/provider/hello/{msg}")
    String hello(@PathVariable("msg") String msg);
}
