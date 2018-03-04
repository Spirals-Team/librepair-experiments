package pl.wasper.bandmanagement.event.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "place")
    private String place;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "advance")
    private BigDecimal advance;

    @Column(name = "isAdvancePaid")
    private boolean isAdvancePaid;

    @Column(name = "isPaid")
    private boolean isPaid;
}
