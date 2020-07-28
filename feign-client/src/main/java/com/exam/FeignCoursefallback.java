package com.exam;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaogu
 * @date 2020/7/27 18:37
 **/
@Component
public class FeignCoursefallback implements FeignCourse {
    @Override
    public Map listroombyrpc() {
        Map map =new HashMap();
        map.put("error","熔断机制：此服务暂不可用");
        return map;
    }
}
