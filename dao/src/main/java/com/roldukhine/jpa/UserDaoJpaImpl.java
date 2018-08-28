package com.roldukhine.jpa;

import com.roldukhine.api.UserDao;
import com.roldukhine.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoJpaImpl extends AbstractDaoJpaImpl<User> implements UserDao {

}
