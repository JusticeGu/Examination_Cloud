package com.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xiaogu
 * @date 2020/7/30 14:46
 **/
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class ExamCoreDataVisApplication {
    public static void main(String[] args) {
        System.out.println("数据分析服务已启动...");
        SpringApplication.run( ExamCoreDataVisApplication.class, args);
    }

}