package com.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiaogu
 * @date 2020/7/27 11:59
 **/
@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @RequestMapping(value = "/provider/hello/{msg}")
    String hello(@PathVariable("msg") String msg){
        System.out.println("1");
        return "Service_user1:你好："+msg;
    }
    @RestController
    class EchoController {
        @GetMapping (value = "/echo/{string}")
        public String echo(@PathVariable String string) {
            return string;
        }
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
