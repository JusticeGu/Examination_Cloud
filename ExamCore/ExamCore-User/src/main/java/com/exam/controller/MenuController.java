package com.exam.controller;
/**
 * @author xiaogu
 * @date 2020/7/15 19:29
 **/

import com.exam.Entity.AdminMenu;
import com.exam.result.ExceptionMsg;
import com.exam.result.ResponseData;
import com.exam.service.AdminMenuService;
import com.exam.service.RedisService;
import com.exam.service.UserService;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@RestController
@Api(tags = "用户获取菜单接口")
public class MenuController {
    @Autowired
    AdminMenuService adminMenuService;
    @Autowired
    UserService userService;
    @Resource
    private RedisService redisService;

    @GetMapping("/api/menu_1")
    public List<AdminMenu> menu_1() {
        return adminMenuService.getMenusByCurrentUser();
    }
    @RequestMapping(value = "/api/menu/refreshmenu", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseData refreshmecu() throws Exception {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        redisService.del(username+"menu");
        redisService.del("TK:"+username);
        return new ResponseData(ExceptionMsg.SUCCESS,"缓存清理成功");
    }
    @GetMapping("/api/menu")
    public ResponseData menu() {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        Object usermenu = redisService.get(username+"menu");
        if(usermenu == null){
            List<Object> admin_user_menu = new ArrayList<Object>(adminMenuService.getMenusByCurrentUser());
            if(admin_user_menu.size()!=0){
                redisService.set(username+"menu",admin_user_menu,7200);
                usermenu = admin_user_menu;
                return new ResponseData(ExceptionMsg.SUCCESS_GETUSER,usermenu);
            }else {
                return new ResponseData(ExceptionMsg.FAILED_V,"用户不存在,请重新尝试");
            }
        }else {
            return new ResponseData(ExceptionMsg.SUCCESS_GETUSERR,usermenu);
        }
    }
    @GetMapping("/api/admin/role/menu")
    public ResponseData listAllMenus() {
        return new ResponseData(ExceptionMsg.SUCCESS_GETUSER,adminMenuService.getMenusByRoleId(1));
    }

}
