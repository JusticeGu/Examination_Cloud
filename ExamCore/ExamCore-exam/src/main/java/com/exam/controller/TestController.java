package com.exam.controller;

import com.exam.result.ExceptionMsg;
import com.exam.result.ResponseData;
import com.exam.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaogu
 * @date 2020/7/29 18:14
 **/
@RestController
@Api(tags = "考试服务接口")
public class TestController {
    @Autowired
    UserService userService;
    @GetMapping("/username")
    @ApiOperation("远程获取用户信息")
    @CrossOrigin
    public ResponseData listroom(){
        String username = userService.getusernamebysu();
        //逻辑
        return new ResponseData(ExceptionMsg.SUCCESS,username);
    }
}
