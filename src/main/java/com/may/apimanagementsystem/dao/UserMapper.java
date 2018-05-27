package com.may.apimanagementsystem.dao;

import com.may.apimanagementsystem.po.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    boolean insertUser(User user);

    boolean deleteUser(int userId);

    boolean updateUser(User user);

    User findUserByUserId(int userId);

    User findUserByUserName(String userName);

    List<User> searchUsersByUserName(String userName);

}
