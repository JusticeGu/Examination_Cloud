package com.exam;

import io.swagger.annotations.ApiOperation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @author xiaogu
 * @date 2020/7/27 18:35
 **/
@FeignClient(value = "ExamCoreService",configuration = FeignConfig.class,fallback = FeignCoursefallback.class)
public interface FeignCourse {
    @GetMapping("/api/course/listfeign")
    Map listroombyrpc();
}
