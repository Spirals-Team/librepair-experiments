package touk.parkingmeter.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import touk.parkingmeter.domain.*;
import touk.parkingmeter.repository.TicketRepository;
import touk.parkingmeter.service.ChargeService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChargeServiceImplUnitTest {

    @Mock
    private TicketRepository ticketRepository;

    private ChargeService chargeService;

    private static final int HOURS_DIFFERENCE = 4;

    private static final int MINUTES_DIFFERENCE =25;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        chargeService = new ChargeServiceImpl(ticketRepository);
    }

    @Test
    public void calculateChargeForEndedTicket() {
        LocalDateTime start = LocalDateTime.now().minusHours(HOURS_DIFFERENCE).minusMinutes(MINUTES_DIFFERENCE);
        LocalDateTime end = LocalDateTime.now();
        Ticket regular = createTicketForRegularDriver(start, end);
        Ticket disabled = createTicketForDisabledDriver(start, end);

        BigDecimal regularCharge = chargeService.calculateCharge(regular);
        BigDecimal regularExp = new BigDecimal(17.25).setScale(2, BigDecimal.ROUND_HALF_UP);

        assertEquals(regularExp, regularCharge);

        BigDecimal disabledCharge = chargeService.calculateCharge(disabled);
        BigDecimal disabledExp = new BigDecimal(10.74).setScale(2, BigDecimal.ROUND_HALF_UP);

        assertEquals(disabledExp, disabledCharge);
    }


    @Test
    public void calculateChargeForNotEndedTicket() {
        LocalDateTime start = LocalDateTime.now().minusHours(HOURS_DIFFERENCE).minusMinutes(MINUTES_DIFFERENCE);
        LocalDateTime end = null;
        Ticket regular = createTicketForRegularDriver(start, end);
        Ticket disabled = createTicketForDisabledDriver(start, end);

        BigDecimal regularCharge = chargeService.calculateCharge(regular);
        BigDecimal regularExp = new BigDecimal(17.25).setScale(2, BigDecimal.ROUND_HALF_UP);

        assertEquals(regularExp, regularCharge);

        BigDecimal disabledCharge = chargeService.calculateCharge(disabled);
        BigDecimal disabledExp = new BigDecimal(10.74).setScale(2, BigDecimal.ROUND_HALF_UP);

        assertEquals(disabledExp, disabledCharge);
    }


    private Ticket createTicketForRegularDriver(LocalDateTime start, LocalDateTime end) {
        Rate rate = RateCreator.createRate(DriverType.REGULAR, Currency.PLN, 1, 2, 1.5);
        Ticket ticket =TicketCreator.createTicket("", start, end, rate, null);
        return ticket;
    }

    private Ticket createTicketForDisabledDriver(LocalDateTime start, LocalDateTime end) {
        Rate rate = RateCreator.createRate(DriverType.DISABLED, Currency.PLN, 0, 2, 1.2);
        Ticket ticket =TicketCreator.createTicket("", start, end, rate, null);
        return ticket;
    }

    @Test
    public void calculateIncomeFromDay() {
        final double[] charges = {23.4, 5.6, 25.8};
        List<Ticket> tickets = createTicketListWithCharges(charges);

        when(ticketRepository.findAllByStartBetween(any(), any())).thenReturn(tickets);

        LocalDate d = LocalDate.now();
        BigDecimal income = chargeService.calculateIncomeFromDay(d);
        BigDecimal expectedIncome = new BigDecimal(DoubleStream.of(charges).sum());

        assertEquals(expectedIncome.setScale(2, BigDecimal.ROUND_HALF_UP), income);
    }

    private List<Ticket> createTicketListWithCharges(double ... charges) {
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < charges.length; i++) {
            Ticket t = mock(Ticket.class);
            when(t.getCharge()).thenReturn(new BigDecimal(charges[i]));
            LocalDateTime s = LocalDateTime.now();
            when(t.getStart()).thenReturn(s);
            when(t.getEnd()).thenReturn(s);
            tickets.add(t);
        }
        return tickets;
    }
}