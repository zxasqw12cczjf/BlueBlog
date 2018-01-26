package com.mrlonely.common.config;

import com.mrlonely.web.system.entity.SysUser;
import com.mrlonely.web.system.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;

    /**
     * 为当限前登录的用户授予角色和权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 验证当前登录的用户
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName=(String)token.getPrincipal();
        SysUser u = new SysUser();
        u.setUsername(userName);
        SysUser sysUser = sysUserService.findByUsername(u);
        if(sysUser!=null){
//            SecurityUtils.getSubject().getSession().setAttribute("currentUser", sysUser); // 当前用户信息存到session中
            AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(sysUser,sysUser.getPassword(),getName());
            return authcInfo;
        }else{
            return null;
        }
    }
}
