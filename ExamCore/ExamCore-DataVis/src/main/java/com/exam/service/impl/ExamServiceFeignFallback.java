package com.exam.service.impl;

import com.exam.entity.Questions;
import com.exam.service.ExamServiceFeign;
import org.springframework.stereotype.Component;

/**
 * @author xiaogu
 * @date 2020/7/30 15:09
 **/
@Component
public class ExamServiceFeignFallback implements ExamServiceFeign {
    @Override
    public Questions getfeignqu(int qid) {
        return null;
    }
}
