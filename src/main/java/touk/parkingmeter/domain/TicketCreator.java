package touk.parkingmeter.domain;

import touk.parkingmeter.dto.TicketDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;


public class TicketCreator {

    public static Ticket createFromDto(TicketDto dto, Rate rate) {
        return Ticket.builder()
                .plateNumber(dto.getPlateNumber())
                .rate(rate)
                .start(dto.getStart())
                .build();
    }

    public static Ticket createTicket(String plate, LocalDateTime strat, LocalDateTime end,
                                      Rate rate, BigDecimal charge) {
        return Ticket.builder()
                .plateNumber(plate)
                .start(strat)
                .end(end)
                .rate(rate)
                .charge(charge)
                .build();
    }

}
