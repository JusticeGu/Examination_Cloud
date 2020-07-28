package com.exam;

/**
 * @author xiaogu
 * @date 2020/7/27 16:32
 **/

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class TestController {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired(required = false)
    private TestService  echoService;

    @Autowired
    private Config  config;

    /**
     * nacos配置测试
     * @return
     */
    @GetMapping (value = "/config-test")
    public String testConfig (){
        log.info ("nacos配置文件属性http.url={}",config.getUrl ());
        return config.getUrl ();
    }

    /**
     *  RestTemplate 请求
     * @param str
     * @return
     */
    @GetMapping (value = "/echo-rest/{str}")
    public String rest(@PathVariable String str) {

        String  result =  restTemplate.getForObject("http://povideri/echo/" + str, String.class);

        log.info ("服务生产者返回的结果：{}",result);
        return  result;
    }

    /**
     *  FeignClient 请求
     * @param str
     * @return
     */
    @GetMapping(value = "/echo-feign/{str}")
    public String feign(@PathVariable String str) {
        return echoService.echo(str);
    }
}

