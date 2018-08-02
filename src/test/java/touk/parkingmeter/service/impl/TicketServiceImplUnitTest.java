package touk.parkingmeter.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import touk.parkingmeter.domain.*;
import touk.parkingmeter.dto.TicketDto;
import touk.parkingmeter.repository.RateRepository;
import touk.parkingmeter.repository.TicketRepository;
import touk.parkingmeter.service.ChargeService;
import touk.parkingmeter.service.TicketService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class TicketServiceImplUnitTest {
    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private RateRepository rateRepository;

    @Mock
    private ChargeService chargeService;

    private TicketService ticketService;

    static final String PLATE_NUMBER = "www123";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ticketService = new TicketServiceImpl(ticketRepository, rateRepository, chargeService);
    }

    @Test
    public void startTicket() {
        TicketDto ticketDto = new TicketDto(PLATE_NUMBER, DriverType.REGULAR, Currency.PLN,
                                            null, null, null);

        when(ticketRepository.findByPlateNumber(ticketDto.getPlateNumber())).thenReturn(Optional.empty());
        when(rateRepository.findByCurrencyAndDriverType(ticketDto.getCurrency().toString(), ticketDto.getDriverType().toString()))
                .thenReturn(Optional.of(new Rate()));

        TicketDto ticket = ticketService.startTicket(ticketDto);

        assertNotNull(ticket.getStart());
    }

    @Test
    public void endTicket() {
        Rate rate = RateCreator.createRate(DriverType.REGULAR, Currency.PLN, 1, 2, 1.5);
        Ticket ticket = TicketCreator.createTicket(PLATE_NUMBER, LocalDateTime.now(), null, rate, null);

        when(ticketRepository.findByPlateNumber(PLATE_NUMBER)).thenReturn(Optional.of(ticket));
        when(chargeService.calculateCharge(ticket)).thenReturn(new BigDecimal(1));

        TicketDto endedTicket = ticketService.endTicket(PLATE_NUMBER);

        assertNotNull(endedTicket.getEnd());
        assertNotNull(endedTicket.getCharge());
    }

}