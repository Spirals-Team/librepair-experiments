package com.roldukhine.entity;

import com.roldukhine.api.UserDao;
import org.junit.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private User admin = createUser("admin", "admin");

    @Test
    public void shouldExistUser() {
        UserDao userDaoMock = mock(UserDao.class);
        UserService userService = new UserServiceImpl(userDaoMock);

        when(userDaoMock.getAll()).thenReturn(Collections.singletonList(admin));

        assertNotNull(userService.checkUser(admin.getLogin(), admin.getPassword()));
    }

    @Test
    public void shouldNotExistUser() {
        UserDao userDaoMock = mock(UserDao.class);
        UserService userService = new UserServiceImpl(userDaoMock);

        assertNull(userService.checkUser(admin.getLogin(), admin.getPassword()));
    }

    private static User createUser(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }
}
