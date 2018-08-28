package com.roldukhine.jdbc;

import com.roldukhine.api.UserDao;
import com.roldukhine.configuration.DaoConfiguration;
import com.roldukhine.configuration.DaoConfigurationTest;
import com.roldukhine.configuration.JdbcConfiguration;
import com.roldukhine.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {DaoConfiguration.class, DaoConfigurationTest.class, JdbcConfiguration.class})
@ActiveProfiles("jdbc")
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Test
    void testInsert() {
        User user = createTestUser();
        userDao.insert(user);
        Assertions.assertNotEquals(0, user.getId());
    }

    @Test
    void testSelect() {
        User user = createTestUser();
        userDao.insert(user);
        Assertions.assertEquals(userDao.get(user.getId()), user);
    }

    @Test
    void testDelete() {
        User user = createTestUser();
        userDao.insert(user);
        userDao.delete(user);
        Assertions.assertNull(userDao.get(user.getId()));
    }

    @Test
    void testUpdate() {
        User user = createTestUser();
        userDao.insert(user);
        user.setLogin("admin");
        userDao.update(user);
        User userById = userDao.get(user.getId());
        Assertions.assertEquals(user.getLogin(), userById.getLogin());
    }

    private User createTestUser() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        return user;
    }
}
