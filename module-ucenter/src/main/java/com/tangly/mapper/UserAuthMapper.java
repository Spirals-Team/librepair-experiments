package com.tangly.mapper;

import com.tangly.base.BaseMybatisMapper;
import com.tangly.entity.UserAuth;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthMapper extends BaseMybatisMapper<UserAuth> {

    UserAuth getUserAuth(String loginAccount);
}