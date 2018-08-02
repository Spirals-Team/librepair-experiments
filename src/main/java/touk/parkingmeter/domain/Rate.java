package touk.parkingmeter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "RATES")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "DRIVER_TYPE")
    private String driverType;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "FIRST_HOUR")
    private double firstHour;

    @Column(name = "SECOND_HOUR")
    private double secondHour;

    @Column(name = "NEXT_HOUR")
    private double nextHour;

}
