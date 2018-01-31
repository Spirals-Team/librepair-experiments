package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.NotFoundException;

import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static org.junit.Assert.assertTrue;
import static ru.javawebinar.topjava.TestHelper.ADMIN;
import static ru.javawebinar.topjava.TestHelper.EMAIL;
import static ru.javawebinar.topjava.TestHelper.ID_ADMIN;
import static ru.javawebinar.topjava.TestHelper.ID_USER;
import static ru.javawebinar.topjava.TestHelper.USER;
import static ru.javawebinar.topjava.TestHelper.createJohnDoe;
import static ru.javawebinar.topjava.TestHelper.makeAdmin;

@ContextConfiguration("classpath:/spring/spring-app.xml")
@Sql("classpath:/db/refill.sql")
@RunWith(SpringRunner.class)
@Transactional(REQUIRED)
public class UserServiceImplTest {
    @Autowired
    private UserService srvc;

    @Test
    public void registerNew() throws Exception {
        srvc.register(createJohnDoe());
    }

    @Test
    public void checkRegisteredAssignedId() throws Exception {
        User user = srvc.register(createJohnDoe());
        assertTrue(!user.isNew());
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerNonNew() throws Exception {
        srvc.register(createJohnDoe().setId(-100));
    }

    @Test
    public void registerDuplicateEmail() throws Exception {
        srvc.register(createJohnDoe().setEmail(ADMIN.getEmail()));
    }

    @Test(expected = InsufficientPrivilegesException.class)
    public void registerAdmin() throws Exception {
        User admin = makeAdmin(createJohnDoe());
        assertTrue(admin.isNew());
        srvc.register(admin);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNew() throws Exception {
        srvc.update(createJohnDoe(), ADMIN);
    }

    @Test
    public void updateAdmin() throws Exception {
        User user = createJohnDoe().setId(ID_ADMIN);
        srvc.update(user, ADMIN);
    }

    @Test
    public void updateByUser() throws Exception {
        User user = createJohnDoe().setId(ID_USER);
        srvc.update(user, USER);
    }

    @Test(expected = InsufficientPrivilegesException.class)
    public void updateEnabledByUser() throws Exception {
        User user = createJohnDoe().setId(ID_USER);
        user.setEnabled(false);
        srvc.update(user, USER);
    }

    @Test(expected = InsufficientPrivilegesException.class)
    public void removeUserByUser() throws Exception {
        srvc.remove(ID_USER, USER);
    }

    @Test
    public void removeUserByAdmin() throws Exception {
        srvc.remove(ID_USER, ADMIN);
    }

    @Test(expected = InsufficientPrivilegesException.class)
    public void removeLastAdminByAdmin() throws Exception {
        srvc.remove(ID_ADMIN, ADMIN);
    }

    @Test
    public void removeNonLastAdminByAdmin() throws Exception {
        User admin = createJohnDoe();
        srvc.register(admin);
        makeAdmin(admin);
        srvc.update(admin, admin);
        srvc.remove(admin.getId(), ADMIN);
    }

    @Test(expected = InsufficientPrivilegesException.class)
    public void getAnotherUserByUser() throws Exception {
        srvc.get(ID_ADMIN, USER);
    }

    @Test
    public void getSameUserByUser() throws Exception {
        srvc.get(ID_USER, USER);
    }

    @Test
    public void getUserByAdmin() throws Exception {
        srvc.get(ID_USER, ADMIN);
    }

    @Test
    public void getAdminByAdmin() throws Exception {
        srvc.get(ID_ADMIN, ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void getNonExistingUserByUser() throws Exception {
        srvc.get(-100, USER);
    }

    @Test(expected = NotFoundException.class)
    public void getNonExistingUserByAdmin() throws Exception {
        srvc.get(-100, ADMIN);
    }

    @Test(expected = InsufficientPrivilegesException.class)
    public void getAnotherEmailByUser() throws Exception {
        srvc.getByEmail(ADMIN.getEmail(), USER);
    }

    @Test
    public void getSameEmailByUser() throws Exception {
        srvc.getByEmail(USER.getEmail(), USER);
    }

    @Test
    public void getUserEmailByAdmin() throws Exception {
        srvc.getByEmail(USER.getEmail(), ADMIN);
    }

    @Test
    public void getAdminEmailByAdmin() throws Exception {
        srvc.getByEmail(ADMIN.getEmail(), ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void getNonExistingEmailByUser() throws Exception {
        srvc.getByEmail(EMAIL, USER);
    }

    @Test(expected = NotFoundException.class)
    public void getNonExistingEmailByAdmin() throws Exception {
        srvc.getByEmail(EMAIL, ADMIN);
    }

    @Test(expected = InsufficientPrivilegesException.class)
    public void listByUser() throws Exception {
        srvc.list(USER);
    }

    @Test
    public void listByAdmin() throws Exception {
        srvc.list(ADMIN);
    }
}
