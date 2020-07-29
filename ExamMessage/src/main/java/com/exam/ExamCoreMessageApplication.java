package com.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xiaogu
 * @date 2020/7/29 01:18
 **/
@EnableDiscoveryClient
@SpringBootApplication
public class ExamCoreMessageApplication {
    public static void main(String[] args) {
        System.out.println("消息服务已启动...");
        SpringApplication.run( ExamCoreMessageApplication.class, args);
    }
}
