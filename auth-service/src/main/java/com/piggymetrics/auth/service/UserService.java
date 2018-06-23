package com.piggymetrics.auth.service;

import com.piggymetrics.auth.domain.User;

/**
 * @author yibo
 */
public interface UserService {

    /**
     * 新建用户
     *
     * @param user
     */
    void create(User user);

}
