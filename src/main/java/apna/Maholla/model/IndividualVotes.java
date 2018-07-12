package apna.Maholla.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "IndividualVotes")
public class IndividualVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Users users;

    @ManyToOne
    private Polling polling;

    @NotBlank
    private boolean poll;
}
