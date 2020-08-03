package com.exam.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.exam.Entity.User;
import com.exam.dao.UserDAO;
import com.exam.rabbit.SenderA;
import com.exam.result.ExceptionMsg;
import com.exam.result.ResponseData;
import com.exam.service.AdminUserRoleService;
import com.exam.service.FallBackService;
import com.exam.service.RedisService;
import com.exam.service.UserService;
import com.exam.util.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;
/**
 * @author xiaogu
 * @date 2020/7/15 19:29
 **/
@RestController
@Api(tags = "用户登录各类相关接口")
public class LoginController implements Serializable {
    @Autowired
    UserService userService;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private SenderA queueSender;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Resource
    private RedisService redisService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/api/register")
    @CrossOrigin
    @ApiOperation("用户注册接口")
    @SentinelResource(value = "register", blockHandler = "handleException_register", blockHandlerClass = {FallBackService.class})
    public ResponseData register(@RequestBody User user,@RequestParam("code") String code) {
        if(!userService.checkmailcode(user.getEmail(),code)){
            return new ResponseData(ExceptionMsg.FAILED,"验证码错误，请重新输入");
        }
        int status = userService.register(user);
        switch (status) {
            case 0:
                return new ResponseData(ExceptionMsg.FAILED,"用户名密码不可以为空");
            case 1:
                return new ResponseData(ExceptionMsg.SUCCESS,user.getUsername());
            case 2:
                return new ResponseData(ExceptionMsg.FAILED_F,"用户已存在");
        }
        return new ResponseData(ExceptionMsg.FAILED_F,"后端错误");
    }
    @PostMapping("/api/tearegister")
    @CrossOrigin
    @ApiOperation("教师注册后台接口")
    public ResponseData msgregister(@RequestBody User user) {
        int status = userService.msgregister(user);
        switch (status) {
            case 0:
                return new ResponseData(ExceptionMsg.FAILED,"用户名密码不可以为空!");
            case 1:
                return new ResponseData(ExceptionMsg.SUCCESS,user.getUsername());
            case 2:
                return new ResponseData(ExceptionMsg.FAILED_F,"用户已存在");
            case 3:
                return new ResponseData(ExceptionMsg.FAILED_F,"逻辑错误");
        }
        return new ResponseData(ExceptionMsg.FAILED_F,"后端错误");
    }
    @PostMapping("/api/login")
    @CrossOrigin
    @ApiOperation("登录接口")
    public ResponseData login(@RequestBody User requestUser) {
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,requestUser.getUser_password());
        try {
            subject.login(usernamePasswordToken);
            User user = userService.findByUsername(username);
            if (!user.isEnabled()) {
                return new ResponseData(ExceptionMsg.Login_FAILED_1,"用户状态异常，请联系客服人员处理");
            }
            String token = JwtUtils.sign(username,JwtUtils.SECRET_KEY);
            Map<String,Object> userinfo = new Hashtable<>();
            userinfo.put("token",token);
            userinfo.put("username",username);
            userinfo.put("uno",user.getUno());
            userinfo.put("uid",user.getUId());
            userinfo.put("role",adminUserRoleService.listAllByUid(user.getUId()));
            redisService.hmset("TK:"+user.getUsername(),userinfo,1800);
            return new ResponseData(ExceptionMsg.SUCCESS,userinfo);
        } catch (IncorrectCredentialsException e) {
            return new ResponseData(ExceptionMsg.Login_FAILED_1,"用户名或密码错误");
        } catch (UnknownAccountException e) {
            return new ResponseData(ExceptionMsg.Login_FAILED_1,"用户名或密码错误");
        }
    }


    @ResponseBody
    @GetMapping("api/logout")
    @CrossOrigin
    @ApiOperation("登出接口")
    public ResponseData logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        //   redisService.del();
        String message = "成功登出";
        return new ResponseData(ExceptionMsg.SUCCESS,message);
    }
    @ResponseBody
    @PutMapping("/api/user/resetpwd")
    @CrossOrigin
    @ApiOperation("重置密码接口")
    public ResponseData resetpwd(@RequestBody User user){
        int status = userService.resetpwd(user.getUsername(),user.getName(),user.getUser_password());
        switch (status) {
            case 1:
                return new ResponseData(ExceptionMsg.SUCCESS,"修改成功");
            case -1:
                return new ResponseData(ExceptionMsg.FAILED_F,"修改链接已失效，请重新获取");
            case 3:
                return new ResponseData(ExceptionMsg.FAILED_F,"后端错误-5");
        }
        return new ResponseData(ExceptionMsg.FAILED_F,"后端错误");
    }
    @PutMapping("/api/admin/user/password")
    @ApiOperation("后台用户密码初始化接口")
    public ResponseData resetPassword(@RequestBody User requestUser) {
        if (userService.resetPassword(requestUser)) {
            return new ResponseData(ExceptionMsg.SUCCESS,"密码已初始化为手机号后6位");
        } else {
            return new ResponseData(ExceptionMsg.SUCCESS,"参数错误，重置失败");
        }
    }
    @ResponseBody
    @GetMapping("api/au")
    @ApiOperation("用户登录状态监测接口")
    @CrossOrigin
    public ResponseData au() {
        try {
            Object subject= SecurityUtils.getSubject().getPrincipal();
            String username = JwtUtils.getUsername(subject.toString());
            return new ResponseData(ExceptionMsg.SUCCESS,username);
        }
        catch (Exception e){
            return new ResponseData(ExceptionMsg.FAILED,"用户未登录");
        }
    }
    @ResponseBody
    @GetMapping("/api/aufeign")
    @ApiOperation("用户登录状态远程方法接口")
    @CrossOrigin
    public String aufeign() {
        try {
            String username = SecurityUtils.getSubject().getPrincipal().toString();
            System.out.println("success:"+username);
            return username;
        }
        catch (Exception e){
            return "undefine";
        }
    }

    @GetMapping("api/authentication")
    @CrossOrigin
    @ApiOperation("用户登录状态监测接口2")
    public ResponseData authentication() {
        try {
            String username = SecurityUtils.getSubject().getPrincipal().toString();
            User user = userService.findByUsername(username);
            return new ResponseData(ExceptionMsg.SUCCESS,user.getUsername());
        }
        catch (Exception e){
            return new ResponseData(ExceptionMsg.FAILED,"fail");
        }

    }

    @RequestMapping(value = "/api/carefresh", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseData refresh(String cache) throws Exception {
        redisService.del(cache);
        return new ResponseData(ExceptionMsg.SUCCESS,"缓存已清除");
    }
    @RequestMapping(value = "/api/getcach", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseData getcache1(String cache) throws Exception {
        Long ucache = redisService.getExpire(cache);
        return new ResponseData(ExceptionMsg.SUCCESS,ucache);
    }

}
