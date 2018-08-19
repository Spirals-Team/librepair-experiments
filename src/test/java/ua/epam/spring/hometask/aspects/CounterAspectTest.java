package ua.epam.spring.hometask.aspects;

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
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;
import ua.epam.spring.hometask.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.*;

import static ua.epam.spring.hometask.EventTestData.*;
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
public class CounterAspectTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CounterAspect counterAspect;

    @Autowired
    private UserService userService;

    @Autowired
    private AuditoriumService auditoriumService;

    private UserAccount userAccount;
    private Auditorium auditorium;
    private NavigableMap<LocalDateTime, Auditorium> auditoriumMap1;
    private NavigableMap<LocalDateTime, Auditorium> auditoriumMap2;
    private NavigableMap<LocalDateTime, Auditorium> auditoriumMap3;


    @Before
    public void setUp() {
        auditorium = auditoriumService.getByName("alpha");

        auditoriumMap1 = new TreeMap<>();
        auditoriumMap2 = new TreeMap<>();
        auditoriumMap3 = new TreeMap<>();
        for (LocalDateTime airDate : EVENT_AIR_DATES1) {
            auditoriumMap1.put(airDate, auditorium);
        }
        for (LocalDateTime airDate : EVENT_AIR_DATES2) {
            auditoriumMap2.put(airDate, auditorium);
        }
        for (LocalDateTime airDate : EVENT_AIR_DATES3) {
            auditoriumMap3.put(airDate, auditorium);
        }


        eventService.save(EventTestData.createNew(EVENT_NAME1, EVENT_PRICE1, EVENT_RATING1, EVENT_AIR_DATES1, auditoriumMap1));
        eventService.save(EventTestData.createNew(EVENT_NAME2, EVENT_PRICE2, EVENT_RATING2, EVENT_AIR_DATES2, auditoriumMap2));
        eventService.save(EventTestData.createNew(EVENT_NAME3, EVENT_PRICE3, EVENT_RATING3, EVENT_AIR_DATES3, auditoriumMap3));

        userService.save(UserTestData.createNew(EMAIL1, USER_NAME1, LAST_NAME1, USER_BIRTHDAY1, PASSWORD, ROLE_SET));

        userAccount = new UserAccount(userService.getUserByEmail(EMAIL1).getId(), "userAcc", 100);
    }

    @Test
    public void printStatistics() throws Exception {

        User user = userService.getUserByEmail(EMAIL1);

        Event event1 = eventService.getByName(EVENT_NAME1);// event1-byName - 1
        Event event2 = eventService.getByName(EVENT_NAME2);// event2-byName - 1

        Set<Ticket> tickets = new TreeSet<>(Arrays.asList(
                new Ticket(userService.getUserByEmail(EMAIL1), event1, NOW, 1L),
                new Ticket(userService.getUserByEmail(EMAIL1), event2, NOW, 2L)
        ));
        Set<Ticket> tickets2 = new TreeSet<>(Arrays.asList(
                new Ticket(userService.getUserByEmail(EMAIL1), event2, NOW, 1L),
                new Ticket(userService.getUserByEmail(EMAIL1), event2, NOW, 2L),
                new Ticket(userService.getUserByEmail(EMAIL1), event2, NOW, 2L)
        ));

        try {
            eventService.getByName("event0");
            Assert.fail("NotFoundException is expecting");
        } catch (NotFoundException e) {
        }

        eventService.getByName(EVENT_NAME1);// event1-byName - 2
        eventService.getByName(EVENT_NAME1);// event1-byName - 3
        eventService.getByName(EVENT_NAME2);// event2-byName - 2
        eventService.getByName(EVENT_NAME2);// event2-byName- 3
        eventService.getByName(EVENT_NAME2);// event2-byName - 4
        try {
            eventService.getByName("event3");
            Assert.fail("NotFoundException is expecting");
        } catch (NotFoundException e) {
        }

        bookingService.getTicketsPrice(event1, AIR_DATE1, USER1, new HashSet<>(Arrays.asList(1L, 2L, 3L)));
        bookingService.getTicketsPrice(event2, AIR_DATE2, USER1, new HashSet<>(Arrays.asList(1L, 2L, 3L)));
        bookingService.getTicketsPrice(event2, AIR_DATE2, USER1, new HashSet<>(Arrays.asList(1L, 2L, 3L)));

//        bookingService.bookTickets(tickets, user.getId(), userAccount, 10);
//        bookingService.bookTickets(tickets, user.getId(), userAccount, 10);
//
//        bookingService.bookTickets(tickets2, user.getId(), userAccount, 10);
//        bookingService.bookTickets(tickets2, user.getId(), userAccount, 10);

        counterAspect.printStatistics();

    }
}