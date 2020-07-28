package com.exam.service;

/**
 * @author xiaogu
 * @date 2020/7/28 17:15
 **/
public interface EmailService {
    public boolean sendTextEmail(String text,String to,String subject);
}
