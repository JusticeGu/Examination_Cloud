package com.exam;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author xiaogu
 * @date 2020/7/27 16:31
 **/
@Component
@Data
@RefreshScope //刷新配置属性的值
public class Config {
    @Value("${http.url}")
    private String url;

}
