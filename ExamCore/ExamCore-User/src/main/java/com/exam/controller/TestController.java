package com.exam.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSONObject;

import com.exam.rabbit.SenderA;
import com.exam.result.ExceptionMsg;
import com.exam.result.ResponseData;
import com.exam.service.FallBackService;
import com.exam.service.RedisService;
import com.exam.util.ExceptionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 * @author xiaogu
 * @date 2020/7/15 19:29
 **/
@RestController
@Api(tags = "新增功能测试专用接口")
public class TestController implements Serializable {

    @Autowired
    private SenderA queueSender;
    @Resource
    private RedisService redisService;

    private static final long serialVersionUID = 3033545151355633240L;


    @RequestMapping(value="/test/sendmail",method = RequestMethod.POST)
    @CrossOrigin
    public ResponseData sendemail(String to,String title,String content){
        if (to.isEmpty()||title.isEmpty()||content.isEmpty()){
            return new ResponseData(ExceptionMsg.FAILED_F,"字段不完整，请完整填写所有字段");}
        Map map = new HashMap();
        map.put("to", to);
        map.put("text", content);
        map.put("subject", title);
        queueSender.send(map);
        return new ResponseData(ExceptionMsg.SUCCESS,"发送成功");
    }

    @PostMapping(value = "/testreceive")
    @CrossOrigin
    @ApiOperation("前端POST请求测试")
    @SentinelResource(value = "testpost", blockHandler = "handleException_post", blockHandlerClass = {ExceptionUtil.class})
    public ResponseData testpost(@RequestBody Object str) {
        Object content = str;
        return new ResponseData(ExceptionMsg.SUCCESS,content);
    }

    @SentinelResource(value = "resource2", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    @RequestMapping(value="/sentinel/test2")
    public ResponseData test2() {
        Map<String,Object> map=new HashMap<>();
        map.put("method","test2");
        map.put("msg","自定义限流逻辑处理");
        return new ResponseData(ExceptionMsg.SUCCESS,map);
    }
    @GetMapping(value = "/redisget")
    @CrossOrigin
    public ResponseData redistest(String key){
        Map user = redisService.hmget(key);
        return new ResponseData(ExceptionMsg.SUCCESS,user);
    }


}
