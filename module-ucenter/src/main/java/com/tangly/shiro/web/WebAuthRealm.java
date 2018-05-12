package com.tangly.shiro.web;//package com.tangly.config.shiro.web;
//
//import com.tangly.entity.SysPermission;
//import com.tangly.entity.SysRole;
//import com.tangly.entity.UserAuth;
//import com.tangly.service.UserAuthService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.cache.Cache;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.util.ByteSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ObjectUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@Slf4j
//public class WebAuthRealm extends AuthorizingRealm {
//
//    @Autowired
//    private UserAuthService userAuthService;
//
//
//    /**
//     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
//
//        // 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
//        // (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
//        if (!SecurityUtils.getSubject().isAuthenticated()) {
//            doClearCache(principals);
//            SecurityUtils.getSubject().logout();
//            return null;
//        }
//
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//
//        UserAuth userAuth =  (UserAuth)principals.getPrimaryPrincipal();
//
//        List<String> permissions = new ArrayList<>();
//        if(!ObjectUtils.isEmpty(userAuth.getSysRoleList())){
//            for(SysRole role : userAuth.getSysRoleList()){
//                authorizationInfo.addRole(role.getRole());
//               if(!ObjectUtils.isEmpty( role.getSysPermissionList())){
//                   for(SysPermission pms : role.getSysPermissionList()){
//                       if(!ObjectUtils.isEmpty(pms)){
//                           permissions.add(pms.getName());
//                       }
//                   }
//               }
//            }
//        }
//        authorizationInfo.addStringPermissions(permissions);
//        log.info("权限",authorizationInfo);
//        return authorizationInfo;
//    }
//
//    /**
//     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        System.out.println("WebAuthRealm.doGetAuthenticationInfo()");
//        String username = (String)token.getPrincipal();
//
//        UserAuth userAuth = userAuthService.getUserAuth(username);
//
//        if (userAuth == null) {
//            throw new UnknownAccountException("用户不存在");
//        }
//
//        if(!userAuth.getAvailable()){
//            throw new DisabledAccountException("帐号已经禁止登录！");
//        }
//
//        clearAuthorizationInfoCache(userAuth);
//
//        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//                //用户
//                userAuth,
//                //密码
//                userAuth.getLoginPassword(),
//                //salt=username+salt
//                ByteSource.Util.bytes(userAuth.getCredentialsSalt()),
//                //realm name
//                "web_realm"
//        );
//
//        return authenticationInfo;
//    }
//
//    /**
//     * 清除所有用户的缓存
//     */
//    public void clearAuthorizationInfoCache() {
//        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
//        if(cache!=null) {
//            cache.clear();
//        }
//    }
//
//    /**
//     * 清除指定用户的缓存
//     * @param userAuth
//     */
//    private void clearAuthorizationInfoCache(UserAuth userAuth) {
//        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
//        cache.remove(userAuth.getLoginAccount());
//    }
//}