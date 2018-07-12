package apna.Maholla.model;

import javax.persistence.*;

@Entity
@Table(name = "State")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    public String stateName;

    @ManyToOne
    private Country country;
}
