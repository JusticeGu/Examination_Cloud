package com.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xiaogu
 * @date 2020/7/27 12:09
 **/
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class FeignClientAoolication {
    public static void main(String[] args) {
        SpringApplication.run(FeignClientAoolication.class, args);
    }
}
