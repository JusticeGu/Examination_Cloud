package com.exam.service.impl;

import com.exam.entity.Questions;
import com.exam.service.ExamServiceFeign;
import com.exam.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaogu
 * @date 2020/7/30 15:19
 **/
@Service
public class QuestionsServiceimpl implements QuestionsService {
    @Autowired
    ExamServiceFeign examServiceFeign;
    @Override
    public Questions getquestionbyid(int qid) {
        return examServiceFeign.getfeignqu(qid);
    }
}
