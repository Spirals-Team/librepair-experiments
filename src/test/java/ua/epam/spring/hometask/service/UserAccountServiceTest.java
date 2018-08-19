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
import ua.epam.spring.hometask.domain.UserAccount;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import static ua.epam.spring.hometask.AccountTestData.*;
import static ua.epam.spring.hometask.AccountTestData.MATCHER;
import static ua.epam.spring.hometask.UserTestData.*;

@ContextConfiguration(
        classes = AppConfig.class,
        loader = AnnotationConfigContextLoader.class
)
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:populate_db.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserAccountServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAccountService accountService;

    @Before
    public void setUp() {
        userService.save(UserTestData.createNew(EMAIL1, USER_NAME1, LAST_NAME1, USER_BIRTHDAY1, PASSWORD, ROLE_SET));
        long userId = userService.getUserByEmail(EMAIL1).getId();

        accountService.save(new UserAccount(userId, ACCOUNT_NAME_1_1, MONEY_1_1), userId);
        accountService.save(new UserAccount(userId, ACCOUNT_NAME_1_2, MONEY_1_2), userId);
        accountService.save(new UserAccount(userId, ACCOUNT_NAME_1_3, MONEY_1_3), userId);
    }

    @Test
    public void buy() {
    //TODO
    }

    @Test
    public void save() {
        long userId = userService.getUserByEmail(EMAIL1).getId();
        UserAccount account_2_3 = new UserAccount(userId, ACCOUNT_NAME_1_2 + "_3", MONEY_1_2);
        accountService.save(account_2_3, userId);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_ACCOUNT1, USER_ACCOUNT2, account_2_3, USER_ACCOUNT3),
                accountService.getAll(userId)
                        .stream().sorted(Comparator.comparing(UserAccount::getName).thenComparing(UserAccount::getMoney))
                        .collect(Collectors.toList()));
    }

    @Test
    public void remove() {
        long userId = userService.getUserByEmail(EMAIL1).getId();
        UserAccount account_1_2 = accountService.getByName(ACCOUNT_NAME_1_2, userId);

        accountService.remove(account_1_2, userId);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_ACCOUNT1, USER_ACCOUNT3),
                accountService.getAll(userId)
                        .stream().sorted(Comparator.comparing(UserAccount::getName).thenComparing(UserAccount::getMoney))
                        .collect(Collectors.toList()));
    }

    @Test
    public void getById() {
        long userId = userService.getUserByEmail(EMAIL1).getId();
        UserAccount actual_account = accountService.getByName(ACCOUNT_NAME_1_2, userId);
        MATCHER.assertEquals(USER_ACCOUNT2, accountService.getById(actual_account.getId(), userId));
    }

    @Test
    public void getAll() {
        long userId = userService.getUserByEmail(EMAIL1).getId();
        MATCHER.assertCollectionEquals(Arrays.asList(USER_ACCOUNT1, USER_ACCOUNT2, USER_ACCOUNT3),
                accountService.getAll(userId)
                        .stream()
                        .sorted(Comparator.comparing(UserAccount::getName).thenComparing(UserAccount::getMoney))
                        .collect(Collectors.toList()));
    }
}