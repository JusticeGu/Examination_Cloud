package com.exam;

import com.exam.config.DruidConfig;
import com.exam.result.ExceptionMsg;
import com.exam.result.ResponseData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xiaogu
 * @date 2020/7/27 17:52
 **/
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class ExamCoreExamApplication {
    public static void main(String[] args) {
        System.out.println("考试核心服务已启动...");
        SpringApplication.run( ExamCoreExamApplication.class, args);
    }

}
