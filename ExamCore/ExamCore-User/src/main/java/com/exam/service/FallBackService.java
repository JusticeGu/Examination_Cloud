package com.exam.service;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.exam.Entity.User;
import com.exam.result.ExceptionMsg;
import com.exam.result.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xiaogu
 * @date 2020/7/31 11:54
 **/
@Slf4j
public class FallBackService {

    public static ResponseData handleException () {
        log.error ("异常降级处理");
        //可以处理各种类型的异常，自定义异常
        return new ResponseData(ExceptionMsg.FALL_BACK,"请不要频繁访问此服务");
    }
    public static ResponseData handleException_resetpassword (@RequestBody User user, BlockException ex) {

        //可以处理各种类型的异常，自定义异常
        return new ResponseData(ExceptionMsg.FALL_BACK,"请不要频繁进行操作");
    }
    public static ResponseData handleException_register (@RequestBody User user, @RequestParam("code") String code, BlockException ex) {

        //可以处理各种类型的异常，自定义异常
        return new ResponseData(ExceptionMsg.FALL_BACK,"请不要频繁注册");
    }
    public static ResponseData handleException_sendcode (String to, BlockException ex) {

        //可以处理各种类型的异常，自定义异常
        return new ResponseData(ExceptionMsg.FALL_BACK,"请不要频繁访问此服务");
    }
}
