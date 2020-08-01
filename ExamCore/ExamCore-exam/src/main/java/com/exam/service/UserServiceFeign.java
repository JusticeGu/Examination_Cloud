package com.exam.service;

import com.exam.config.FeignConfiguration;
import com.exam.service.impl.UserServiceFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xiaogu
 * @date 2020/7/29 14:39
 **/
@FeignClient(value = "UserCoreService",configuration = FeignConfiguration.class,fallback = UserServiceFeignFallback.class)
public interface UserServiceFeign {
    @GetMapping("api/aufeign")
    String aufeign();
}
