package ua.epam.spring.hometask.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ua.epam.spring.hometask.UserTestData;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.User;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import static ua.epam.spring.hometask.UserTestData.*;

/**
 * Created by Vladimir on 09.10.2017.
 */
@ContextConfiguration(
        classes = AppConfig.class,
        loader = AnnotationConfigContextLoader.class
)
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:populate_db.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Before
    public void setUp() throws Exception {
        service.save(UserTestData.createNew(EMAIL1, USER_NAME1, LAST_NAME1, USER_BIRTHDAY1, PASSWORD, ROLE_SET));
        service.save(UserTestData.createNew(EMAIL2, USER_NAME2, LAST_NAME2, USER_BIRTHDAY1, PASSWORD, ROLE_SET));
        service.save(UserTestData.createNew(EMAIL3, USER_NAME3, LAST_NAME3, USER_BIRTHDAY1, PASSWORD, ROLE_SET));
    }

    @Test
    public void getUserByEmail() throws Exception {
        MATCHER.assertEquals(USER1, service.getUserByEmail(EMAIL1));
        MATCHER.assertEquals(USER2, service.getUserByEmail(EMAIL2));
        MATCHER.assertEquals(USER3, service.getUserByEmail(EMAIL3));
    }

    @Test
    public void save() throws Exception {
        User user0 = UserTestData.createNew("user0@epam.com", "User0", "UserLast0", USER_BIRTHDAY1, PASSWORD, ROLE_SET);
        service.save(user0);

        MATCHER.assertCollectionEquals(
                Arrays.asList(user0, USER1, USER2, USER3),
                service.getAll()
                        .stream()
                        .sorted(Comparator.comparing(User::getFirstName).thenComparing(User::getLastName).thenComparing(User::getEmail))
                        .collect(Collectors.toList()));
    }

    @Test
    public void remove() throws Exception {
        User user2 = service.getUserByEmail(EMAIL2);
        service.remove(user2);
        MATCHER.assertCollectionEquals(
                Arrays.asList(USER1, USER3),
                service.getAll()
                        .stream()
                        .sorted(Comparator.comparing(User::getFirstName).thenComparing(User::getLastName).thenComparing(User::getEmail))
                        .collect(Collectors.toList()));
    }

    @Test
    public void getById() throws Exception {
        User user2 = service.getUserByEmail(EMAIL2);
        MATCHER.assertEquals(USER2, service.getById(user2.getId()));
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(
                Arrays.asList(USER1, USER2, USER3),
                service.getAll()
                        .stream()
                        .sorted(Comparator.comparing(User::getFirstName).thenComparing(User::getLastName).thenComparing(User::getEmail))
                        .collect(Collectors.toList()));
    }
}