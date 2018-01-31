package ru.javawebinar.topjava.repository.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.NotFoundException;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Collection;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ru.javawebinar.topjava.TestHelper.ADMIN;
import static ru.javawebinar.topjava.TestHelper.ID_ADMIN;
import static ru.javawebinar.topjava.TestHelper.ID_USER;
import static ru.javawebinar.topjava.TestHelper.USER;
import static ru.javawebinar.topjava.TestHelper.createJohnDoe;
import static ru.javawebinar.topjava.TestHelper.makeAdmin;

/** @author danis.tazeev@gmail.com */
@ContextConfiguration("classpath:/spring/spring-app.xml")
@Sql("classpath:/db/refill.sql")
@RunWith(SpringRunner.class)
@Transactional(REQUIRED)
public class JpaUserRepositoryImplTest {
    private static final int ID_NON_EXISTING = -100;

    @Autowired
    private UserRepository repo;

    @Test
    public void addNewUser() throws Exception {
        repo.add(createJohnDoe());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNonNewUser() throws Exception {
        repo.add(createJohnDoe().setId(ID_NON_EXISTING));
    }

    @Test
//    @Test(expected = DataIntegrityViolationException.class)
//    @Rollback(false)
    public void addDuplicateEmailUser() throws Exception {
        repo.add(createJohnDoe().setEmail(ADMIN.getEmail()));
    }

    @Test
    public void updateExistingUser() throws Exception {
        repo.update(createJohnDoe().setId(ID_ADMIN));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateWithNewUser() throws Exception {
        repo.update(createJohnDoe());
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFoundUser() throws Exception {
        repo.update(createJohnDoe().setId(ID_NON_EXISTING));
    }

    @Test
//    @Test(expected = DataIntegrityViolationException.class)
//    @Rollback(false)
    public void updateWithDuplicateEmail() throws Exception {
        repo.update(createJohnDoe().setEmail(USER.getEmail()).setId(ID_ADMIN));
    }

    @Test
    public void removeExistingUser() throws Exception {
        repo.remove(ID_USER);
    }

    @Test(expected = NotFoundException.class)
    public void removeNotExistingUser() throws Exception {
        repo.remove(ID_NON_EXISTING);
    }

    @Test
    public void getExistingUser() throws Exception {
        User user = repo.get(ID_USER);
        assertEquals((Integer)ID_USER, user.getId());
    }

    @Test(expected = NotFoundException.class)
    public void getNonExistingUser() throws Exception {
        repo.get(ID_NON_EXISTING);
    }

    @Test
    public void list() throws Exception {
        Collection<User> users = repo.list();
        User admin = createJohnDoe().setId(ID_ADMIN);
        User user = createJohnDoe().setId(ID_USER);
        assertThat(users, hasItems(admin, user));
    }

    @Test
    public void onlyOneAdminLeft() throws Exception {
        assertTrue(repo.atMostOneAdminLeft());
    }

    @Test
    public void atLeastTwoAdminsLeft() throws Exception {
        repo.add(makeAdmin(createJohnDoe()));
        assertFalse(repo.atMostOneAdminLeft());
    }
}
