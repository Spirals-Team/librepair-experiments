package com.may.apimanagementsystem.service;

import com.may.apimanagementsystem.dao.UserMapper;
import com.may.apimanagementsystem.exception.ParameterException;
import com.may.apimanagementsystem.exception.ServerException;
import com.may.apimanagementsystem.po.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.may.apimanagementsystem.constant.ExceptionMessage.*;
import static com.may.apimanagementsystem.util.FormatUtil.isEmail;
import static com.may.apimanagementsystem.util.FormatUtil.isPhoneNumberLegal;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public void addUser(User user) {
        checkAddUserParameter(user);
        if (!userMapper.insertUser(user))
            throw new ServerException();
    }

    public User checkUser(User user) {
        User getUser = userMapper.findUserByUserName(user.getUserName());
        Objects.requireNonNull(getUser, USERNAME_NOT_EXIST);
        if (!user.getPassword().equals(getUser.getPassword()))
            throw new ParameterException(PASSWORD_WRONG);
        return user;
    }

    private void checkAddUserParameter(User user) {
        if (user.getUserName() == null || user.getPassword() == null ||
                user.getEmail() == null)
            throw new ParameterException(PARAMETER_CANNOT_NULL);
        checkUserName(user.getUserName());
        checkPassword(user.getPassword());
        checkEmail(user.getEmail());
    }

    public void removeUser(int userId) {
        if (!userMapper.deleteUser(userId))
            throw new ServerException();
    }

    public void updateUser(User user) {
        checkUpdateUserParameter(user);
        if (!userMapper.updateUser(user))
            throw new ServerException();
    }

    private void checkUpdateUserParameter(User user) {
        if (user.getUserName() != null)
            checkUserName(user.getUserName());
        if (user.getEmail() != null)
            checkEmail(user.getEmail());
        if (user.getPassword() != null)
            checkPassword(user.getPassword());
        if (user.getPhoneNumber() != null)
            if (isPhoneNumberLegal(user.getPhoneNumber()))
                throw new ParameterException(PHONENUMBER_WRONG);
        if (user.getSex() != null)
            if (!user.getSex().equals("男") && !user.getSex().equals("女"))
                throw new ParameterException(SEX_WRONG);
    }

    private void checkUserName(String userName) {
        User user = userMapper.findUserByUserName(userName);
        if (user != null && !user.getUserName().equals(userName))
            throw new ParameterException(DOUBLE_USER_NAME);
        if (userName.length() > 20)
            throw new ParameterException(USER_NAME_IS_TOO_LONG);
    }

    private void checkEmail(String email) {
        if (!isEmail(email))
            throw new ParameterException(EMAIL_FORMAT_WRONG);
    }

    private void checkPassword(String password) {
        if (password.length() > 20)
            throw new ParameterException(PASSWORD_IS_TOO_LONG);
    }

    public User getUserByUserId(int userId) {
        User user = userMapper.findUserByUserId(userId);
        Objects.requireNonNull(user);
        return user;
    }

    public List<User> searchUserByUserName(String userName) {

        return userMapper.searchUsersByUserName(userName);
    }
}
