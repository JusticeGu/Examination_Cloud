package com.exam.service;

import org.springframework.stereotype.Service;

/**
 * @author xiaogu
 * @date 2020/7/29 14:35
 **/
public interface UserService {
    public String getusername();
    public String getusernamebysu();
    public String getUnoByUsername(String username);

}
