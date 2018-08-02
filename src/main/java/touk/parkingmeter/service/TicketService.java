package touk.parkingmeter.service;

import touk.parkingmeter.dto.TicketDto;

public interface TicketService {
    TicketDto startTicket(TicketDto ticketDto);
    TicketDto endTicket(String plateNumber);
    TicketDto checkTicket(String plateNumber);
}
