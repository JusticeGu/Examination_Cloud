package com.exam.service.impl;

import com.exam.rabbit.SenderA;
import com.exam.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaogu
 * @date 2020/7/28 17:16
 **/
@Service
public class EmailServiceimpl implements EmailService {
    @Autowired
    SenderA queueSender;
    @Override
    public boolean sendTextEmail(String text, String to, String subject) {
        if (text.isEmpty()||to.isEmpty()||subject.isEmpty()){
            return false;}
        Map map = new HashMap();
        map.put("to", to);
        map.put("text", text);
        map.put("subject",subject);
        queueSender.send(map);
        return true;
    }
}
