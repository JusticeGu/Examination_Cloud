package com.exam.config;
import cn.hutool.core.lang.Console;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.exam.Entity.User;
import com.exam.service.RedisService;
import com.exam.service.UserService;
import com.exam.util.JWTToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
/**
 * @author xiaogu
 * @date 2020/7/24 17:52
 **/
import org.springframework.beans.factory.annotation.Autowired;



/**
 * JwtRealm 只负责校验 JwtToken
 */
public class JwtRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    /**
     * 限定这个 Realm 只处理我们自定义的 JwtToken
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 此处的 SimpleAuthenticationInfo 可返回任意值，密码校验时不会用到它
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        JWTToken jwtToken = (JWTToken) authcToken;
        if (jwtToken.getPrincipal() == null) {
            throw new AccountException("JWT token参数异常！");
        }
        // 从 JwtToken 中获取当前用户
        String username = jwtToken.getPrincipal().toString();
        String tk = "TK:"+username;
        // 查询数据库获取用户信息，此处使用 Map 来模拟数据库
        //    User user = userService.findByUsername(username);
        Object user = redisService.hget(tk,"token");
        // 用户不存在
        if (user == null) {
            throw new UnknownAccountException("用户不存在！");
        }


        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, user.toString(), getName());
        Console.log(username+"JWT登录成功");
        return info;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取当前用户
        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        // UserEntity currentUser = (UserEntity) principals.getPrimaryPrincipal();
        // 查询数据库，获取用户的角色信息
    //    Set<String> roles = ShiroRealm.roleMap.get(currentUser.getName());
        // 查询数据库，获取用户的权限信息
    //    Set<String> perms = ShiroRealm.permMap.get(currentUser.getName());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    //    info.setRoles(roles);
    //    info.setStringPermissions(perms);
        return info;
    }


}