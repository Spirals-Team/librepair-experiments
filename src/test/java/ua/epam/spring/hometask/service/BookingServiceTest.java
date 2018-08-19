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
import ua.epam.spring.hometask.UserTestData;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.util.exception.BookingException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.Assert.fail;
import static ua.epam.spring.hometask.EventTestData.EVENT_PRICE1;
import static ua.epam.spring.hometask.EventTestData.EVENT_PRICE2;
import static ua.epam.spring.hometask.UserTestData.*;

@ContextConfiguration(
        classes = AppConfig.class,
        loader = AnnotationConfigContextLoader.class
)

@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:populate_db.sql", config = @SqlConfig(encoding = "UTF-8"))
public class BookingServiceTest {

    @Autowired
    BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAccountService accountService;

    @Autowired
    private EventService eventService;


    private UserAccount userAccount;
    private LocalDateTime ldt;
    private Auditorium auditorium1;
    private Auditorium auditorium2;
    private NavigableMap<LocalDateTime, Auditorium> auditoriumMap;

    @Before
    public void setUp() {
        userService.save(UserTestData.createNew(EMAIL1, USER_NAME1, LAST_NAME1, USER_BIRTHDAY1, PASSWORD, ROLE_SET));
        long userId = userService.getUserByEmail(EMAIL1).getId();
        userAccount = new UserAccount(userId, "userAcc", 100);
        accountService.save(userAccount, userId);
        ldt = LocalDateTime.now();

        auditorium1 = new Auditorium();
        auditorium1.setName("alpha");
        auditorium1.setNumberOfSeats(10);
        auditorium1.setVipSeats(Stream.of(1L, 2L, 3L).collect(Collectors.toSet()));

        auditorium2 = new Auditorium();
        auditorium2.setName("beta");
        auditorium2.setNumberOfSeats(9);
        auditorium2.setVipSeats(Stream.of(5L).collect(Collectors.toSet()));

        auditoriumMap = new TreeMap<>();
        auditoriumMap.put(ldt, auditorium1);
        auditoriumMap.put(ldt.plusDays(1), auditorium2);

    }

    @Test
    public void getTicketsPrice() throws Exception {
        User user = userService.getUserByEmail(EMAIL1);

        Event event = new Event();
        event.setBasePrice(10);
        event.setName("test_event");
        event.setRating(EventRating.MID);
        event.setAirDates(new TreeSet<>(Arrays.asList(ldt.minusDays(1), ldt.plusDays(1), ldt)));
        event.setAuditoriums(auditoriumMap);

        Assert.assertEquals(110, bookingService.getTicketsPrice(event, ldt, user, LongStream.range(1, 9).boxed().collect(Collectors.toSet())), 0);
        Assert.assertEquals(218.5, bookingService.getTicketsPrice(event, ldt, user, LongStream.range(1, 21).boxed().collect(Collectors.toSet())), 0);
        Assert.assertEquals(503.5, bookingService.getTicketsPrice(event, ldt, user, LongStream.range(1, 51).boxed().collect(Collectors.toSet())), 0);

    }

    @Test
    public void bookTicketsSize() throws Exception {

        User user = userService.getUserByEmail(EMAIL1);

        Event event = new Event();
        event.setBasePrice(10);
        event.setName("event1");
        event.setRating(EventRating.HIGH);
        event.setAirDates(new TreeSet<>(Arrays.asList(ldt, ldt.plusDays(1))));
        event.setAuditoriums(auditoriumMap);
        eventService.save(event);

        Set<Ticket> tickets = new TreeSet<>(Arrays.asList(
                new Ticket(user, event, ldt, 1L),
                new Ticket(user, event, ldt, 2L)
        ));
        bookingService.bookTickets(tickets, user.getId(), userAccount, 10);

        Assert.assertEquals(2, bookingService.getPurchasedTicketsForEvent(event, ldt).size());
    }

    @Test
    public void bookTicketsDuplicate() throws Exception {

        User user = userService.getUserByEmail(EMAIL1);

        Event event = new Event();
        event.setName("event1");
        event.setBasePrice(EVENT_PRICE1);
        event.setRating(EventRating.MID);
        event.setAirDates(new TreeSet<>(Arrays.asList(ldt, ldt.plusDays(1))));
        event.setAuditoriums(auditoriumMap);

        eventService.save(event);

        Set<Ticket> tickets = new TreeSet<>(Arrays.asList(
                new Ticket(user, event, ldt, 2L),
                new Ticket(user, event, ldt, 3L)
        ));
        bookingService.bookTickets(tickets, user.getId(), userAccount, 10);

        tickets = new TreeSet<>(Arrays.asList(
                new Ticket(user, event, ldt, 3L),
                new Ticket(user, event, ldt, 4L)
        ));

        try {
            bookingService.bookTickets(tickets, user.getId(), userAccount, 10);
            fail("Duplicating booking should throw BookingException");
        } catch (BookingException e) {
        }

        Assert.assertEquals(2, bookingService.getPurchasedTicketsForEvent(event, ldt).size());
    }

    @Test
    public void getPurchasedTicketsForEvent() throws Exception {
        User user = userService.getUserByEmail(EMAIL1);

        Event event1 = new Event();
        event1.setName("event");
        event1.setBasePrice(EVENT_PRICE1);
        event1.setRating(EventRating.MID);
        event1.setAirDates(new TreeSet<>(Arrays.asList(ldt, ldt.plusDays(1))));
        event1.setAuditoriums(auditoriumMap);

        eventService.save(event1);

        Set<Ticket> tickets = new TreeSet<>(Arrays.asList(
                new Ticket(user, event1, ldt, 1L),
                new Ticket(user, event1, ldt, 2L)
        ));
        bookingService.bookTickets(tickets, user.getId(), userAccount, 10);

        Event event2 = new Event();
        event2.setName("event2");
        event2.setBasePrice(EVENT_PRICE2);
        event2.setRating(EventRating.MID);
        event2.setAirDates(new TreeSet<>(Arrays.asList(ldt.plusDays(2), ldt.plusDays(3))));
        NavigableMap<LocalDateTime, Auditorium> auditoriumMap2 = new TreeMap<>();
        auditoriumMap2.put(ldt.plusDays(2), auditorium1);
        auditoriumMap2.put(ldt.plusDays(3), auditorium2);
        event2.setAuditoriums(auditoriumMap2);

        eventService.save(event2);

        Set<Ticket> tickets2 = new TreeSet<>(Arrays.asList(
                new Ticket(user, event2, ldt.plusDays(2), 1L),
                new Ticket(user, event2, ldt.plusDays(2), 2L),
                new Ticket(user, event2, ldt.plusDays(2), 2L),
                new Ticket(user, event2, ldt.plusDays(3), 3L),
                new Ticket(user, event2, ldt.plusDays(3), 4L),
                new Ticket(user, event2, ldt.plusDays(3), 5L),
                new Ticket(user, event2, ldt.plusDays(3), 6L)
        ));
        bookingService.bookTickets(tickets2, user.getId(), userAccount, 10);

        Assert.assertEquals(0, bookingService.getPurchasedTicketsForEvent(event2, ldt).size());
        Assert.assertEquals(4, bookingService.getPurchasedTicketsForEvent(event2, ldt.plusDays(3)).size());
    }
}