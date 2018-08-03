package com.cmpl.web.core.user;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.User;

public interface UserDAO extends BaseDAO<User> {

  User findByLogin(String login);

  User findByEmail(String email);

}
