package com.exam.service;

import com.exam.config.FeignConfiguration;
import com.exam.entity.Questions;
import com.exam.service.impl.ExamServiceFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xiaogu
 * @date 2020/7/30 15:09
 **/

@FeignClient(value = "ExamCoreService",configuration = FeignConfiguration.class,fallback = ExamServiceFeignFallback.class)
public interface ExamServiceFeign {
    @RequestMapping(method = RequestMethod.GET, value = "/api/question/feignqu")
    public Questions getfeignqu(@RequestParam("qid")int qid);
}