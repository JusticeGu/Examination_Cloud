package com.exam.service;

import com.exam.Entity.User;
import com.exam.dto.UserDTO;
import java.util.List;


public interface UserService {
    public List<UserDTO> list();
    public boolean resetPassword(User user);
    public boolean changePassword(String oldpwd,String pwd);
    public boolean usernamemailcheck(String username,String email);//用户名邮箱是否匹配
    public boolean isExist(String username);
    public boolean unoisExist(String uno);
    public String usernametouno(String username);
    public boolean isable(String username);
    public boolean wxisable(String wxuid);
    public boolean wxisExist(String wxuid);
    public boolean deleteUser(User user);
    public User findByUsername(String username);
    public User findByWx_uid(String wxuid);
    public void addOrUpdate(User user);
    public boolean updateUserStatus(User user);
    public boolean editUser(User user);
    public int unoBind(String phone,String code,String uno);
    public int register(User user);
    public int msgregister(User user);
    public int resetpwd(String usrname,String pwd,String newpwd);
    public String getusernamebysu();
    public User geyuserbytoken();
    public String sendmailsecode(String mail);
    public String sengmailvalidurl(String username);
    public boolean checkmailcode(String mail,String code);
    public String getUnoByUsername(String username);



}




