package com.tangly.shiro.jwt;

import com.tangly.entity.SysPermission;
import com.tangly.entity.SysRole;
import com.tangly.entity.UserAuth;
import com.tangly.service.IUserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangly
 */
@Component(value = "jwtRealm")
@Slf4j
public class JWTRealm extends AuthorizingRealm {

    @Autowired
    private IUserAuthService iUserAuthService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserAuth userAuth = (UserAuth) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();


        List<String> permissions = new ArrayList();
        if(!ObjectUtils.isEmpty(userAuth.getSysRoleList())){
            for(SysRole role : userAuth.getSysRoleList()){
                simpleAuthorizationInfo.addRole(role.getRole());
                if(!ObjectUtils.isEmpty( role.getSysPermissionList())){
                    for(SysPermission pms : role.getSysPermissionList()){
                        if(!ObjectUtils.isEmpty(pms)){
                            permissions.add(pms.getName());
                        }
                    }
                }
            }
        }
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;

    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String jwt = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(jwt);
        if (username == null) {
            throw new AuthenticationException("无效的token");
        }

        UserAuth userAuth = iUserAuthService.getUserAuth(username);

        if (userAuth == null) {
            throw new AuthenticationException("User didn't existed!");
        }

        if (! JWTUtil.verify( jwt, username, userAuth.getLoginPassword())) {
            throw new AuthenticationException("Username or password error");
        }

        return new SimpleAuthenticationInfo(userAuth, jwt, "my_realm");
    }
}