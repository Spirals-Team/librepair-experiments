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
import ua.epam.spring.hometask.EventTestData;
import ua.epam.spring.hometask.UserTestData;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;

import java.time.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ua.epam.spring.hometask.EventTestData.*;
import static ua.epam.spring.hometask.EventTestData.MATCHER;
import static ua.epam.spring.hometask.UserTestData.*;

/**
 * Created by Vladimir on 11.10.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = AppConfig.class,
        loader = AnnotationConfigContextLoader.class
)
@Sql(scripts = "classpath:populate_db.sql", config = @SqlConfig(encoding = "UTF-8"))
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    private Auditorium auditorium;
    private NavigableMap<LocalDateTime, Auditorium> auditoriumMap1;
    private NavigableMap<LocalDateTime, Auditorium> auditoriumMap2;
    private NavigableMap<LocalDateTime, Auditorium> auditoriumMap3;

    @Before
    public void setUp() throws Exception {

        userService.save(UserTestData.createNew(EMAIL1, USER_NAME1, LAST_NAME1, USER_BIRTHDAY1, PASSWORD, ROLE_SET));

        auditorium = new Auditorium();
        auditorium.setName("alpha");
        auditorium.setNumberOfSeats(10);
        auditorium.setVipSeats(Stream.of(1L, 2L, 3L).collect(Collectors.toSet()));

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
    }

    @Test
    public void getByName() throws Exception {
        MATCHER.assertEquals(EVENT1, eventService.getByName(EVENT_NAME1));
        MATCHER.assertEquals(EVENT2, eventService.getByName(EVENT_NAME2));
        MATCHER.assertEquals(EVENT3, eventService.getByName(EVENT_NAME3));
    }

    @Test
    public void save() throws Exception {
        Event event5 = EventTestData.createNew("event5", 100, EventRating.HIGH, EVENT_AIR_DATES1, auditoriumMap1);
        eventService.save(event5);
        MATCHER.assertCollectionEquals(Arrays.asList(EVENT1, EVENT2, EVENT3, event5),
                eventService.getAll()
                        .stream()
                        .sorted(Comparator.comparing(Event::getName).thenComparing(Event::getBasePrice).thenComparing(Event::getRating))
                        .collect(Collectors.toList()));

    }

    @Test
    public void remove() throws Exception {
        Event event2 = eventService.getByName(EVENT_NAME2);

        eventService.remove(event2);
        MATCHER.assertCollectionEquals(
                Arrays.asList(EVENT1, EVENT3),
                eventService.getAll()
                        .stream()
                        .sorted(Comparator.comparing(Event::getName).thenComparing(Event::getBasePrice).thenComparing(Event::getRating))
                        .collect(Collectors.toList()));
    }

    @Test
    public void getById() throws Exception {
        Event event3 = eventService.getByName(EVENT_NAME3);
        MATCHER.assertEquals(EVENT3, eventService.getById(event3.getId()));
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(
                Arrays.asList(EVENT1, EVENT2, EVENT3),
                eventService.getAll()
                        .stream()
                        .sorted(Comparator.comparing(Event::getName).thenComparing(Event::getBasePrice).thenComparing(Event::getRating))
                        .collect(Collectors.toList()));
    }

    @Test
    public void getForDateRange() throws Exception {
        MATCHER.assertCollectionEquals(
                Arrays.asList(EVENT1, EVENT3),
                eventService.getForDateRange(LocalDate.of(2018, 1, 25), LocalDate.of(2018, 2, 5))
                        .stream()
                        .sorted(Comparator.comparing(Event::getName).thenComparing(Event::getBasePrice).thenComparing(Event::getRating))
                        .collect(Collectors.toList()));
    }

    @Test
    public void getNextEvents() throws Exception {
        ZoneId zone = ZoneId.systemDefault();
        Clock clock = Clock.fixed(NOW.toInstant(ZoneOffset.UTC), zone);
        eventService.setClock(clock);

        MATCHER.assertCollectionEquals(
                Arrays.asList(EVENT1, EVENT3),
                eventService.getNextEvents(TO)
                        .stream()
                        .sorted(Comparator.comparing(Event::getName).thenComparing(Event::getBasePrice).thenComparing(Event::getRating))
                        .collect(Collectors.toList())
        );
    }

}