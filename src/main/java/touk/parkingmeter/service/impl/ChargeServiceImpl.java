package touk.parkingmeter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import touk.parkingmeter.domain.Rate;
import touk.parkingmeter.domain.Ticket;
import touk.parkingmeter.repository.TicketRepository;
import touk.parkingmeter.service.ChargeService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ChargeServiceImpl implements ChargeService {

    private TicketRepository ticketRepository;

    @Autowired
    public ChargeServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public BigDecimal calculateCharge(Ticket ticket) {
        LocalDateTime end = ticket.getEnd() != null ? ticket.getEnd() : LocalDateTime.now();
        long hours = calculateHours(ticket.getStart(), end);
        Rate rate = ticket.getRate();

        BigDecimal charge = new BigDecimal(0);

        if (hours > 0) {
            charge = charge.add(new BigDecimal(rate.getFirstHour()));
            hours--;
            if (hours > 0) {
                charge = charge.add(new BigDecimal(rate.getSecondHour()));
                hours--;
                BigDecimal previousPrice = new BigDecimal(rate.getSecondHour());
                while (hours > 0) {
                    previousPrice = previousPrice.multiply(new BigDecimal(rate.getNextHour()));
                    charge = charge.add(previousPrice);
                    hours--;
                }
            }
        }
        return charge.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private Long calculateHours(LocalDateTime start, LocalDateTime end) {
        long hours = start.until(end, ChronoUnit.HOURS) ;
        long minutes = start.until(end, ChronoUnit.MINUTES);
        long seconds = start.until(end, ChronoUnit.SECONDS);

        if (minutes > 0 || seconds > 0) {
            hours++;
        }
        return hours;
    }

    @Override
    public BigDecimal calculateIncomeFromDay(LocalDate date) {
        List<Ticket> tickets = ticketRepository.findAllByStartBetween(date.atStartOfDay(),
                                                                      date.plusDays(1).atStartOfDay());
        BigDecimal income = new BigDecimal(0);
        for (Ticket ticket : tickets) {
            income = income.add(ticket.getCharge());
        }
        return income.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
