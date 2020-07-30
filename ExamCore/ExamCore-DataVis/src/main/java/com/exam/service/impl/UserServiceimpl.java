package com.exam.service.impl;

import com.exam.service.UserService;
import com.exam.service.UserServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaogu
 * @date 2020/7/29 15:05
 **/
@Service
public class UserServiceimpl implements UserService {
    @Autowired
    UserServiceFeign userServiceFeign;
    @Override
    public String getusername() {
        return userServiceFeign.aufeign();
    }

    @Override
    public String getusernamebysu() {
        return userServiceFeign.aufeign();
    }
}
