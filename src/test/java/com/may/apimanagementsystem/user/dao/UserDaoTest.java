package com.may.apimanagementsystem.user.dao;

import com.may.apimanagementsystem.dao.UserMapper;
import com.may.apimanagementsystem.po.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class UserDaoTest {

    @Autowired
    private UserMapper userMapper;
    private User user;

    @Before
    public void before(){
        user = new User();
        user.setUserName("ooo");
        user.setPassword("11111");
        user.setEmail("1058752198@qq.com");
    }

    @Test
    public void deleteUserTest(){
        boolean flag = userMapper.deleteUser(1000);
        assertEquals(true, flag);
    }

    @Test
    public void updateUserTest(){
        user.setUserId(1000);
        boolean flag = userMapper.updateUser(user);
        assertEquals(true, flag);
    }

    @Test
    public void findUserByUserIdTest(){
        User user = userMapper.findUserByUserId(1001);
        assertEquals("qqqq", user.getUserName());
    }

    @Test
    public void findUserByUserUserNameTest(){
        User user = userMapper.findUserByUserName("qqqq");
        assertEquals(1001, user.getUserId());
    }

    @Test
    public void searUserByUserNameTest(){
        List<User> users = userMapper.searchUsersByUserName("%q%");
        assertEquals(1, users.size());
    }
}
