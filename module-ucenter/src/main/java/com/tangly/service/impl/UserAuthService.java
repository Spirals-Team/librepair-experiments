package com.tangly.service.impl;

import com.tangly.base.BaseService;
import com.tangly.entity.UserAuth;
import com.tangly.entity.UserInfo;
import com.tangly.mapper.UserAuthMapper;
import com.tangly.mapper.UserInfoMapper;
import com.tangly.service.IUserAuthService;
import com.tangly.util.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * date: 2018/5/2 10:24 <br/>
 *
 * @author Administrator
 * @since JDK 1.7
 */
@Service
public class UserAuthService extends BaseService<UserAuth> implements IUserAuthService {

    @Autowired
    UserAuthMapper userAuthMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    PasswordHelper passwordHelper;

    @Override
    public UserAuth getUserAuth(String loginAccount) {
        UserAuth userAuth = userAuthMapper.getUserAuth(loginAccount);
        return userAuth;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerUserAuth(UserAuth userAuth) {
        passwordHelper.encryptNewPassForUser(userAuth);

        UserInfo userInfo = userAuth.getUserInfo();

        //先存入用户信息
        userInfoMapper.insert(userAuth.getUserInfo());

        //生成关联
        userAuth.setUserId(userInfo.getUserId());
        //存入账号体系
        userAuthMapper.insert(userAuth);
    }

    @Override
    public boolean existUserName(String loginAccount) {
        Example example = new Example(UserAuth.class);
        example.or().andEqualTo("loginAccount",loginAccount);
        if (mapper.selectCountByExample(example) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void save(UserAuth userAuth) {
        mapper.insert(userAuth);
    }
}
