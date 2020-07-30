package com.exam.service;


import com.exam.entity.Answer;

import java.util.Map;

/**
 * @author JunXxxxi
 * @date 2020/7/30 12:29
 **/
public interface AnswerService {
    public int saveAnswer(Answer answer);
    public Map getAnswer(int eid);
    public int upadteAnswer(int eid,int qid,String anscontent);
}
