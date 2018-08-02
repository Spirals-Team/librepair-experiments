package touk.parkingmeter.dto;

import lombok.*;
import touk.parkingmeter.domain.Currency;
import touk.parkingmeter.domain.DriverType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TicketDto {
    private String plateNumber;
    private DriverType driverType;
    private Currency currency;
    private BigDecimal charge;
    private LocalDateTime start;
    private LocalDateTime end;
}
