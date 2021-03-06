package com.exam.controller;

import cn.hutool.core.date.DateUtil;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.exam.Entity.User;
import com.exam.dao.UserDAO;
import com.exam.rabbit.SenderA;
import com.exam.result.ExceptionMsg;
import com.exam.result.ResponseData;
import com.exam.service.AdminRoleService;
import com.exam.service.FallBackService;
import com.exam.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * @author xiaogu
 * @date 2020/7/15 19:29
 **/
@RestController
@Api(tags = "用户管理相关接口")
@RequestMapping("/api/user")
public class UserController implements Serializable {
    private static final long serialVersionUID = 3033545151355633240L;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    AdminRoleService adminRoleService;
    @Autowired
    UserService userService;
    @Autowired
    private SenderA queueSender;
    @RequestMapping(value="/sendcheckcode",method = RequestMethod.POST)
    @CrossOrigin
    @ApiOperation("邮箱验证码发送接口")
    @SentinelResource(value = "sendcode", blockHandler = "handleException_sendcode", blockHandlerClass = {FallBackService.class})
    public ResponseData sendcode(String to){
        String code = userService.sendmailsecode(to);
        String now = DateUtil.now();
        if (code!=null){
            String content = "您于"+now+"申请的邮箱验证码为：【"+code+"】(5分钟有效)如非本人操作请及时修改密码!【河马在线考试运营团队】（此邮箱为系统邮箱请勿回复）";
            queueSender.sendemail(to, "【河马在线考试】您正在进行邮箱验证",content);
            return new ResponseData(ExceptionMsg.SUCCESS,"操作成功");
        }else {
            return new ResponseData(ExceptionMsg.FAILED,"发送失败");
        }
    }
    @RequestMapping(value="/checkcode",method = RequestMethod.POST)
    @CrossOrigin
    @ApiOperation("邮箱验证码验证接口")
    public ResponseData checkcode(String mail,String code){
        if (userService.checkmailcode(mail,code)){
            return new ResponseData(ExceptionMsg.SUCCESS,"验证成功");}
        else {
            return new ResponseData(ExceptionMsg.FAILED,"验证失败");
        }
    }
    @RequestMapping(value="/resetpassword",method = RequestMethod.POST)
    @CrossOrigin
    @ApiOperation("重置密码邮件发送接口")
    @SentinelResource(value = "resetpassword", blockHandler = "handleException_resetpassword", blockHandlerClass = {FallBackService.class})
    public ResponseData resetpassword(@RequestBody User user){
        if (userService.usernamemailcheck(user.getUsername(), user.getEmail())){
            String content = "您正在进行修改密码操作，点击链接修改密码（5分钟有效） "+"http://hippo-exam.q7w.cn/reset_pwd?url="
                    +userService.sengmailvalidurl(user.getUsername())+"&name="+user.getUsername();

            queueSender.sendemail( user.getEmail(), "【河马在线考试】您正在进行重置密码",content);
            return new ResponseData(ExceptionMsg.SUCCESS,"验证成功，重置邮件已发送至您的邮箱");}
        else {
            return new ResponseData(ExceptionMsg.FAILED,"验证失败：用户名与邮箱不匹配,或重复请求");
        }
    }
    @GetMapping("/admin/role_1")
    public ResponseData listRoles_1(){
        return new ResponseData(ExceptionMsg.SUCCESS,adminRoleService.list());
    }
    @GetMapping("/api")
    @CrossOrigin
    public List<User> list()  {

        List<User> userList = userDAO.findAll();
        return userList;
        //return new ResponseData(ExceptionMsg.SUCCESS,userList);
    }
    @PutMapping("/admin/user/status")
    public  ResponseData updateUserStatus(@RequestBody User requestUser) {
        if (userService.updateUserStatus(requestUser)) {
            return new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS");
        } else {
            return new ResponseData(ExceptionMsg.FAILED,"faile");
        }
    }
    @GetMapping("/admin/user")
    @CrossOrigin
    @RequiresRoles("admin")
    public ResponseData listUsers_1() {
        try {
            return new ResponseData(ExceptionMsg.SUCCESS,userService.list());
        } catch (Exception e) {
            return new ResponseData(ExceptionMsg.FAILED,"faile");
        }
    }

    @RequestMapping(value="/api_1",method = RequestMethod.GET)
    @CrossOrigin
    public ResponseData getlist() {
        List<User> userList = new ArrayList<>(userDAO.findAll());
        return new ResponseData(ExceptionMsg.SUCCESS,userList);
    }
    @ApiOperation("添加用户的接口")

    @PutMapping("/admin/user")
    public ResponseData editUser(@RequestBody User requestUser) {
        if(userService.editUser(requestUser)) {
            return new ResponseData(ExceptionMsg.SUCCESS,"SUCCESS");
        } else {
            return new ResponseData(ExceptionMsg.FAILED,"faile");
        }
    }
    @RequestMapping("/user/list")
    public ModelAndView articlelist(@RequestParam(value = "start", defaultValue = "0") Integer start,
                                    @RequestParam(value = "limit", defaultValue = "5") Integer limit) {
        start = start < 0 ? 0 : start;
        Sort sort = Sort.by(Sort.Direction.DESC, "UId");
        Pageable pageable = PageRequest.of(start, limit, sort);
        Page<User> page = userDAO.findAll(pageable);
        ModelAndView mav = new ModelAndView("User/list");
        mav.addObject("page", page);
        return mav;
    }

    //http://localhost:8080/updateUser?Id=1&userName=%E5%A4%A7%E8%80%81%E6%9D%A8&userPassword=1111
    @GetMapping(value = "/updateUser")
    public String updateUser(int UId,String userName,String userPassword){
        User user = new User(UId,userName, userPassword);
        userDAO.save(user);
        return "success";
    }

    @PostMapping("/userdelete")
    public ResponseData deleteUser(@RequestBody User user) {
        if(userService.deleteUser(user)) {
            return new ResponseData(ExceptionMsg.SUCCESS,"删除成功");
        } else {
            return new ResponseData(ExceptionMsg.FAILED,"更新异常");
        }
    }

}
