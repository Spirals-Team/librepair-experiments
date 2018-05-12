package com.tangly.util;

import com.tangly.entity.UserAuth;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Component;

/**
 * @author tangly
 */
@Component
public class PasswordHelper {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    // 散列算法
    private String algorithmName = "md5";
    // 散列次数
    private final int hashIterations = 2;

    /**
     * 根据用户的password字段（未加密） 生成新的密文和盐
     * @param userAuth
     */
    public void encryptNewPassForUser(UserAuth userAuth) {
        //设置初始化salt
        userAuth.setLoginSalt(randomNumberGenerator.nextBytes().toHex());
        //通过salt ,将密码进行加密，这里加密使用的salt是uName+初始化salt
        String newPassword = new SimpleHash(algorithmName, userAuth.getLoginPassword(), userAuth.getCredentialsSalt(), hashIterations).toHex();
        userAuth.setLoginPassword(newPassword);
    }

    /**
     * 验证密码是否正确
     * @param plainPassword
     * @param userAuth
     * @return
     */
    public boolean verifyPassword(String plainPassword , UserAuth userAuth){
       return  userAuth.getLoginPassword().equals(new SimpleHash(algorithmName, plainPassword, userAuth.getCredentialsSalt(), hashIterations).toHex());
    }

    /**
     * 加密密码
     * @param plainPassword
     * @return
     */
    public String encryptPassword(String plainPassword , UserAuth userAuth){
        return new SimpleHash(algorithmName, plainPassword, userAuth.getCredentialsSalt(), hashIterations).toHex();
    }

}