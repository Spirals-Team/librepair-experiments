package ua.epam.spring.hometask.aspects;

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
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.service.UserService;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static ua.epam.spring.hometask.EventTestData.EVENT1;
import static ua.epam.spring.hometask.EventTestData.NOW;
import static ua.epam.spring.hometask.UserTestData.*;

/**
 * Created by Vladimir on 22.10.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = AppConfig.class,
        loader = AnnotationConfigContextLoader.class
)
@Sql(scripts = "classpath:populate_db.sql", config = @SqlConfig(encoding = "UTF-8"))
public class DiscountAspectTest {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private UserService userService;

    @Autowired
    private DiscountAspect counterAspect;

    @Test
    public void collectStatistics() throws Exception {
        userService.save(UserTestData.createNew(EMAIL1, USER_NAME1, LAST_NAME1, USER_BIRTHDAY1, PASSWORD, ROLE_SET));
        userService.save(UserTestData.createNew(EMAIL2, USER_NAME2, LAST_NAME2, USER_BIRTHDAY2, PASSWORD, ROLE_SET));
        User user1 = userService.getUserByEmail(EMAIL1);
        User user2 = userService.getUserByEmail(EMAIL2);

        discountService.getDiscount(user1, EVENT1, NOW, 5);
        discountService.getDiscount(user2, EVENT1, NOW, 15);
        discountService.getDiscount(user1, EVENT1, NOW, 25);
        discountService.getDiscount(user1, EVENT1, LocalDateTime.of(user1.getBirthday(), LocalTime.now()), 5);
        discountService.getDiscount(user1, EVENT1, LocalDateTime.of(user1.getBirthday(), LocalTime.now()), 5);
        discountService.getDiscount(user1, EVENT1, LocalDateTime.of(user1.getBirthday(), LocalTime.now()), 105);
        discountService.getDiscount(user2, EVENT1, LocalDateTime.of(user2.getBirthday(), LocalTime.now()), 5);

        counterAspect.printStatistics();
    }

}