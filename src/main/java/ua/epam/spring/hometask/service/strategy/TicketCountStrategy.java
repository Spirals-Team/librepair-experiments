package ua.epam.spring.hometask.service.strategy;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;

/**
 * Calculate right final percent for all tickets
 * <p>
 * Created by Vladimir on 02.11.2017.
 */
public class TicketCountStrategy implements DiscountStrategy {

    @Override
    public byte getPercentDiscount(User user, Event event, LocalDateTime dateTime, long numberOfTickets) {

        if (numberOfTickets < 10) {
            return 0;
        }
        return (byte) Math.round((double) (numberOfTickets / 10 * 10 * 5) / numberOfTickets);
    }
}
