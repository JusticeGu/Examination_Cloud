package com.exam.util;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.exam.Entity.User;
import com.exam.result.ExceptionMsg;
import com.exam.result.ResponseData;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaogu
 * @date 2020/7/31 12:30
 **/
public class ExceptionUtil {

    public static ResponseData handleException(BlockException ex) {

        return new ResponseData(ExceptionMsg.FALL_BACK,"流控");
    }
    public static ResponseData handleException_resetpassword (@RequestBody User user, BlockException ex) {

        //可以处理各种类型的异常，自定义异常
        return new ResponseData(ExceptionMsg.FALL_BACK,"请不要频繁访问此服务：");
    }
    public static ResponseData handleException_register (@RequestBody User user, @RequestParam("code") String code, BlockException ex) {

        //可以处理各种类型的异常，自定义异常
        return new ResponseData(ExceptionMsg.FALL_BACK,"请不要频繁访问此服务：");
    }
}
