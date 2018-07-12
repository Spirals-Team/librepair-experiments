package apna.Maholla.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "Polling")
public class Polling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    private Apartment Apartment;

    @NotBlank
    private String Subject;

    @NotBlank
    private String Description;

    @NotBlank
    private Date StartDate;

    @NotBlank
    private Date EndDate;

    @NotBlank
    private int totalVoters;

    @NotBlank
    private int votesInFavour;

    @NotBlank
    private int votesAgainstFavour;

    @ManyToOne
    private Roles roles;
}
