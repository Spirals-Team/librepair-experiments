package com.tangly.shiro.web;//package com.tangly.config.shiro.web;
//
//import com.tangly.config.cache.ShiroRedisCacheManager;
//import com.tangly.util.TimeUtil;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.ExcessiveAttemptsException;
//import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
//import org.apache.shiro.cache.Cache;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
///**
// * 密码的校验匹配类
// *
// * @author tangly
// */
//@Component
//public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
//
//    private Cache<String, PassWordRetryLog> passwordRetryCache;
//
//    public RetryLimitHashedCredentialsMatcher(ShiroRedisCacheManager cacheManager) {
//        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
//    }
//
//    private static int MAX_LOGIN_TIME = 5;
//
//    @Override
//    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
//        System.out.println("密码验证");
//        String username = (String) token.getPrincipal();
//        PassWordRetryLog passWordRetryLog = passwordRetryCache.get(username);
//        if (passWordRetryLog == null) {
//            passWordRetryLog = new PassWordRetryLog();
//            passwordRetryCache.put(username, passWordRetryLog);
//        } else {
//            int second = TimeUtil.compareTimeDiffSeconds(
//                    System.currentTimeMillis(),
//                    passWordRetryLog.getLastTryDate().getTime());
//
//            //上一次登录尝试是在24小时前
//            boolean beforeLogin = (second > 86400);
//
//            if (beforeLogin) {
//                passWordRetryLog = new PassWordRetryLog();
//            }
//        }
//
//        passWordRetryLog.setLastTryDate(new Date());
//        passWordRetryLog.getRetryCount().incrementAndGet();
//        passwordRetryCache.put(username, passWordRetryLog);
//
//
//        if (passWordRetryLog.getRetryCount().get() > MAX_LOGIN_TIME) {
//            /**
//             * 登录请求次数过多异常
//             */
//            throw new ExcessiveAttemptsException("错误尝试次数: " + passWordRetryLog.getRetryCount().get());
//        }
//
//        boolean isMatched = super.doCredentialsMatch(token, info);
//        if (isMatched) {
//            passwordRetryCache.remove(username);
//        }
//        return isMatched;
//    }
//}