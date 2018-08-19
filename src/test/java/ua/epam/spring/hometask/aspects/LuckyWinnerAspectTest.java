package ua.epam.spring.hometask.aspects;

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
public class LuckyWinnerAspectTest {

    @Autowired
    EventService eventService;

    @Autowired
    BookingService bookingService;

    @Autowired
    UserService userService;

    @Autowired
    AuditoriumService auditoriumService;

    @Autowired
    LuckyWinnerAspect luckyWinnerAspect;

    private UserAccount userAccount;

    @Before
    public void setUp() {
        NavigableMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();

        for (LocalDateTime ldt : EVENT_AIR_DATES1) {
            auditoriums.put(ldt, auditoriumService.getByName("alpha"));
        }
        NavigableMap<LocalDateTime, Auditorium> auditoriums2 = new TreeMap<>();

        for (LocalDateTime ldt : EVENT_AIR_DATES2) {
            auditoriums2.put(ldt, auditoriumService.getByName("beta"));
        }


        eventService.save(EventTestData.createNew(EVENT_NAME1, EVENT_PRICE1, EVENT_RATING1, EVENT_AIR_DATES1, auditoriums));
        eventService.save(EventTestData.createNew(EVENT_NAME2, EVENT_PRICE2, EVENT_RATING2, EVENT_AIR_DATES2, auditoriums2));

        userService.save(UserTestData.createNew(EMAIL1, USER_NAME1, LAST_NAME1, USER_BIRTHDAY1, PASSWORD, ROLE_SET));
        userService.save(UserTestData.createNew(EMAIL2, USER_NAME2, LAST_NAME2, USER_BIRTHDAY1, PASSWORD, ROLE_SET));

        userAccount = new UserAccount(userService.getUserByEmail(EMAIL1).getId(), "userAcc", 100);
    }


    @Test
    public void tryCatchLuck() throws Exception {

        User user = userService.getUserByEmail(EMAIL1);

        Event event1 = eventService.getByName(EVENT_NAME1);
        Event event2 = eventService.getByName(EVENT_NAME2);

        LocalDateTime ldt = LocalDateTime.of(2018, 1, 1, 10, 0);

        Set<Ticket> tickets1 = new TreeSet<>(Arrays.asList(
                new Ticket(userService.getUserByEmail(EMAIL1), event1, NOW, 1L),
                new Ticket(userService.getUserByEmail(EMAIL1), event1, NOW, 2L)
        ));
        Set<Ticket> tickets2 = new TreeSet<>(Arrays.asList(
                new Ticket(userService.getUserByEmail(EMAIL1), event2, NOW, 1L),
                new Ticket(userService.getUserByEmail(EMAIL1), event2, NOW, 2L),
                new Ticket(userService.getUserByEmail(EMAIL1), event2, NOW, 2L)
        ));
        Set<Ticket> tickets3 = new TreeSet<>(Arrays.asList(
                new Ticket(userService.getUserByEmail(EMAIL1), event1, ldt, 3L),
                new Ticket(userService.getUserByEmail(EMAIL2), event1, ldt, 4L)
        ));

//        bookingService.bookTickets(tickets1, user.getId(), userAccount, 10);
//        bookingService.bookTickets(tickets2, user.getId(), userAccount, 10);
//        bookingService.bookTickets(tickets3, user.getId(), userAccount, 10);
//        bookingService.bookTickets(tickets3, user.getId(), userAccount, 10);
//        bookingService.bookTickets(tickets3, user.getId(), userAccount, 10);
//        bookingService.bookTickets(tickets3, user.getId(), userAccount, 10);
//        bookingService.bookTickets(tickets3, user.getId(), userAccount, 10);
//        bookingService.bookTickets(tickets3, user.getId(), userAccount, 10);
//        bookingService.bookTickets(tickets3, user.getId(), userAccount, 10);
//        bookingService.bookTickets(tickets3, user.getId(), userAccount, 10);
//        bookingService.bookTickets(tickets3, user.getId(), userAccount, 10);

        luckyWinnerAspect.printStatistics();

    }

}