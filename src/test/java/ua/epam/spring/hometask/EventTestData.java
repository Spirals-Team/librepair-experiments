package ua.epam.spring.hometask;

import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.matcher.ModelMatcher;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Vladimir on 11.10.2017.
 */
public class EventTestData {

    public static final ModelMatcher<Event> MATCHER = new ModelMatcher<>(
            Comparator.comparing(Event::getName)
                    .thenComparing(Event::getBasePrice)
                    .thenComparing(Event::getRating));

    public static final Event EVENT1 = new Event();
    public static final Event EVENT2 = new Event();
    public static final Event EVENT3 = new Event();

    public static final String EVENT_NAME1 = "Event1";
    public static final String EVENT_NAME2 = "Event2";
    public static final String EVENT_NAME3 = "Event3";

    public static final double EVENT_PRICE1 = 10;
    public static final double EVENT_PRICE2 = 20;
    public static final double EVENT_PRICE3 = 30;

    public static final LocalDateTime AIR_DATE1 = LocalDateTime.of(2018, 1, 1, 10, 0);    //01-jan-2018 10:00
    public static final LocalDateTime AIR_DATE2 = LocalDateTime.of(2018, 1, 5, 12, 0);    //05-jan-2018 12:00

    public static final TreeSet<LocalDateTime> EVENT_AIR_DATES1 = new TreeSet<>(Arrays.asList(
            LocalDateTime.of(2018, 1, 1, 10, 0),    //01-jan-2018 10:00
            LocalDateTime.of(2018, 1, 15, 15, 0),   //15-jan-2018 15:00
            LocalDateTime.of(2018, 1, 30, 20, 0)    //30-jan-2018 20:00
    ));
    public static final TreeSet<LocalDateTime> EVENT_AIR_DATES2 = new TreeSet<>(Arrays.asList(
            LocalDateTime.of(2018, 1, 5, 12, 0),    //05-jan-2018 12:00
            LocalDateTime.of(2018, 1, 20, 14, 0)    //20-jan-2018 14:00
    ));
    public static final TreeSet<LocalDateTime> EVENT_AIR_DATES3 = new TreeSet<>(Arrays.asList(
            LocalDateTime.of(2018, 2, 2, 21, 0),    //02-feb-2018 21:00
            LocalDateTime.of(2018, 2, 7, 9, 0)      //07-feb-2018 09:00
    ));

    public static final LocalDateTime NOW = LocalDateTime.of(2018, 1, 25, 12, 0, 0);
    public static final LocalDateTime TO = LocalDateTime.of(2018, 2, 5, 12, 0, 0);

    public static final EventRating EVENT_RATING1 = EventRating.LOW;
    public static final EventRating EVENT_RATING2 = EventRating.MID;
    public static final EventRating EVENT_RATING3 = EventRating.HIGH;

    static {
        EVENT1.setName(EVENT_NAME1);
        EVENT1.setBasePrice(EVENT_PRICE1);
        EVENT1.setRating(EVENT_RATING1);
        EVENT1.setAirDates(EVENT_AIR_DATES1);

        EVENT2.setName(EVENT_NAME2);
        EVENT2.setBasePrice(EVENT_PRICE2);
        EVENT2.setRating(EVENT_RATING2);
        EVENT2.setAirDates(EVENT_AIR_DATES2);

        EVENT3.setName(EVENT_NAME3);
        EVENT3.setBasePrice(EVENT_PRICE3);
        EVENT3.setRating(EVENT_RATING3);
        EVENT3.setAirDates(EVENT_AIR_DATES3);

    }

    public static Event createNew(String name, double price, EventRating rating, TreeSet<LocalDateTime> airsDates, NavigableMap<LocalDateTime, Auditorium> auditoriumSet) {
        Event event = new Event();
        event.setName(name);
        event.setBasePrice(price);
        event.setRating(rating);
        event.setAirDates(airsDates);
        event.setAuditoriums(auditoriumSet);
        return event;
    }
}
