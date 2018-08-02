package touk.parkingmeter.service;

import touk.parkingmeter.domain.Ticket;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface ChargeService {
    BigDecimal calculateCharge(Ticket ticket);
    BigDecimal calculateIncomeFromDay(LocalDate date);
}
