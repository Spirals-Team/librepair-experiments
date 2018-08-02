package touk.parkingmeter.domain;

import lombok.*;
import touk.parkingmeter.dto.TicketDto;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TICKETS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "PLATE_NUMBER")
    private String plateNumber;

    @Column(name = "START_TIME")
    private LocalDateTime start;

    @Column(name = "END_TIME")
    private LocalDateTime end;

    @ManyToOne
    @JoinColumn(name = "RATE_ID", nullable = false)
    private Rate rate;

    @Column(name = "CHARGE")
    private BigDecimal charge;

    public TicketDto toDto() {
        return TicketDto.builder()
                .plateNumber(plateNumber)
                .start(start)
                .end(end)
                .driverType(DriverType.valueOf(rate.getDriverType()))
                .currency(Currency.valueOf(rate.getCurrency()))
                .charge(charge)
                .build();
    }
}
