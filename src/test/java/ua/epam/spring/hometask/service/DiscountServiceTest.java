package ua.epam.spring.hometask.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ua.epam.spring.hometask.EventTestData;
import ua.epam.spring.hometask.UserTestData;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;
import java.util.NavigableMap;
import java.util.TreeMap;

import static ua.epam.spring.hometask.EventTestData.*;
import static ua.epam.spring.hometask.UserTestData.*;

@ContextConfiguration(
        classes = AppConfig.class,
        loader = AnnotationConfigContextLoader.class
)
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:populate_db.sql", config = @SqlConfig(encoding = "UTF-8"))
public class DiscountServiceTest {

    @Autowired
    DiscountService discountService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private AuditoriumService auditoriumService;


    private NavigableMap<LocalDateTime, Auditorium> auditoriumMap = new TreeMap<>();


    @Before
    public void setUp() throws Exception {
        userService.save(
                UserTestData.createNew(
                        EMAIL1, USER_NAME1, LAST_NAME1, USER_BIRTHDAY1, PASSWORD, ROLE_SET));//good
        userService.save(
                UserTestData.createNew(
                        EMAIL2, USER_NAME2, LAST_NAME2, USER_BIRTHDAY2, PASSWORD, ROLE_SET));//bad

        Auditorium auditorium = auditoriumService.getByName("gama");

        for (LocalDateTime airDate : EVENT_AIR_DATES1) {
            auditoriumMap.put(airDate, auditorium);
        }

        eventService.save(EventTestData.createNew(EVENT_NAME1, EVENT_PRICE1, EVENT_RATING1, EVENT_AIR_DATES1, auditoriumMap));
    }

    @Test
    public void getDiscount() throws Exception {

        User luckyUser = userService.getUserByEmail(EMAIL1);
        User simpleUser = userService.getUserByEmail(EMAIL2);

        Event event = eventService.getByName(EVENT_NAME1);

        //nothing special
        Assert.assertEquals(0, discountService.getDiscount(simpleUser, event, AIR_DATE1, 3));
        //Birthday is close
        Assert.assertEquals(10, discountService.getDiscount(luckyUser, event, AIR_DATE1, 3));
        //10 tickets
        Assert.assertEquals(5, discountService.getDiscount(simpleUser, event, AIR_DATE1, 10));
        //Birthday is close and 10 tickets
        Assert.assertEquals(10, discountService.getDiscount(luckyUser, event, AIR_DATE1, 10));
    }

}