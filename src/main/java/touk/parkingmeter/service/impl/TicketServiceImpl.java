package touk.parkingmeter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import touk.parkingmeter.domain.Rate;
import touk.parkingmeter.domain.Ticket;
import touk.parkingmeter.domain.TicketCreator;
import touk.parkingmeter.dto.TicketDto;
import touk.parkingmeter.exception.ResourceConflictException;
import touk.parkingmeter.exception.ResourceNotFoundException;
import touk.parkingmeter.repository.RateRepository;
import touk.parkingmeter.repository.TicketRepository;
import touk.parkingmeter.service.ChargeService;
import touk.parkingmeter.service.TicketService;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final RateRepository rateRepository;
    private final ChargeService chargeService;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository,
                             RateRepository rateRepository,
                             ChargeService chargeService) {
        this.ticketRepository = ticketRepository;
        this.rateRepository = rateRepository;
        this.chargeService = chargeService;
    }

    @Override
    public TicketDto startTicket(TicketDto ticketDto) {
        Optional<Ticket> ticketOptional = ticketRepository.findByPlateNumber(ticketDto.getPlateNumber());

        if (ticketOptional.isPresent() && ticketOptional.get().getEnd() == null) {
            throw new ResourceConflictException("Ticket with that plate number already exists");
        } else {
            Optional<Rate> rate = rateRepository.findByCurrencyAndDriverType(ticketDto.getCurrency().toString(),
                                                                              ticketDto.getDriverType().toString());
            if (!rate.isPresent()) {
                throw new ResourceNotFoundException("There is no rate for that currency and driver type");
            } else {
                ticketDto.setStart(LocalDateTime.now());
                Ticket ticket= TicketCreator.createFromDto(ticketDto, rate.get());
                ticketRepository.save(ticket);

                return ticketDto;
            }
        }
    }

    @Override
    public TicketDto endTicket(String plateNumber) {
        Optional<Ticket> ticketOptional = ticketRepository.findByPlateNumber(plateNumber);

        if (!ticketOptional.isPresent()) {
            throw new ResourceNotFoundException("There is no ticket with that plate number");
        } else {
            Ticket ticket = ticketOptional.get();
            if (ticket.getEnd() == null) {
                ticket.setEnd(LocalDateTime.now());
                ticket.setCharge(chargeService.calculateCharge(ticket));
                ticketRepository.save(ticket);
                return ticket.toDto();
            } else {
                throw new ResourceConflictException("Cannot end ticket that has been already ended");
            }
        }
    }

    @Override
    public TicketDto checkTicket(String plateNumber) {
        Optional<Ticket> ticketOptional = ticketRepository.findByPlateNumber(plateNumber);

        if (!ticketOptional.isPresent()) {
            throw new ResourceNotFoundException("There is no ticket with that plate number");
        } else {
            Ticket ticket = ticketOptional.get();
            if (ticket.getEnd() == null) {
                TicketDto ticketDto = ticket.toDto();
                ticketDto.setCharge(chargeService.calculateCharge(ticket));
                return ticketDto;
            } else {
                throw new ResourceNotFoundException("There is no ticket with that plate number that is currently open");
            }
        }
    }
}
