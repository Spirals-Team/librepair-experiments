package com.tangly.service;


import com.tangly.base.IBaseInterface;
import com.tangly.entity.UserAuth;

/**
 * date: 2018/5/2 10:23 <br/>
 * 用户账号接口类
 * @author tangly
 * @since JDK 1.7
 */
public interface IUserAuthService extends IBaseInterface<UserAuth>{

    /**
     * 根据用户登录账号取账号登录实体
     * @param loginAccount
     * @return
     */
    UserAuth getUserAuth(String loginAccount);

    /**
     * 注册用户
     * @param userAuth
     */
    void registerUserAuth(UserAuth userAuth);

    /**
     * 检验用户名是否存在
     * @param loginAccount
     * @return
     */
    boolean existUserName(String loginAccount);

    /**
     * 保存用户
     * @param userAuth
     */
    void save(UserAuth userAuth);
}
