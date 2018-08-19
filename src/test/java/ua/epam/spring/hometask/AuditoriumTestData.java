package ua.epam.spring.hometask;

import ua.epam.spring.hometask.domain.Auditorium;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vladimir on 29.10.2017.
 */
public class AuditoriumTestData {

    public static final Auditorium AUDITORIUM1 = new Auditorium();
    public static final String AUDITORIUM_NAME1 = "alpha";
    public static final long NUMBER_OF_SEATS1 = 10L;
    public static final Set<Long> VIP_SEATS1 = new HashSet<>(Arrays.asList(1L, 2L, 3L));

    public static final Auditorium AUDITORIUM2 = new Auditorium();
    public static final String AUDITORIUM_NAME2 = "beta";
    public static final long NUMBER_OF_SEATS2 = 9L;
    public static final Set<Long> VIP_SEATS2 = new HashSet<>(Arrays.asList(5L));

    public static final Auditorium AUDITORIUM3 = new Auditorium();
    public static final String AUDITORIUM_NAME3 = "gama";
    public static final long NUMBER_OF_SEATS3 = 15L;
    public static final Set<Long> VIP_SEATS3 = new HashSet<>(Arrays.asList(1L, 10L, 15L));

    static {
        AUDITORIUM1.setName(AUDITORIUM_NAME1);
        AUDITORIUM1.setNumberOfSeats(NUMBER_OF_SEATS1);
        AUDITORIUM1.setVipSeats(VIP_SEATS1);

        AUDITORIUM2.setName(AUDITORIUM_NAME2);
        AUDITORIUM2.setNumberOfSeats(NUMBER_OF_SEATS2);
        AUDITORIUM2.setVipSeats(VIP_SEATS2);

        AUDITORIUM3.setName(AUDITORIUM_NAME3);
        AUDITORIUM3.setNumberOfSeats(NUMBER_OF_SEATS3);
        AUDITORIUM3.setVipSeats(VIP_SEATS3);
    }
}
