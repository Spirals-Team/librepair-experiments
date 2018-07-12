package apna.Maholla.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "ApartmentMaintenance")
public class ApartmentMaintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    private Apartment Apartment;

    @ManyToOne
    private Month monthId;

    @NotBlank
    private int Amount;

    @NotBlank
    private int FinePerMonth;

    @NotBlank
    private int LastDayToPay;

    @NotBlank
    private String FineAfter;
}
