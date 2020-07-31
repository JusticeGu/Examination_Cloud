package com.exam;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * @author xiaogu
 * @date 2020/7/27 17:42
 **/
@EnableDiscoveryClient
@SpringBootApplication
public class ExamCoreUserApplication {
    public static void main(String[] args) {
        System.out.println("用户服务已启动...");
        SpringApplication.run( ExamCoreUserApplication.class, args);
    }

}
