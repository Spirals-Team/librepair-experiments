package apna.Maholla.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Maintenance")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Apartment apartment;

    @NotBlank
    private String roomNo;

    @ManyToOne
    private Month monthId;

    @NotBlank
    private int amount;

    @NotBlank
    private boolean isPaid;

}
