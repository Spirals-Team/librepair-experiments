package apna.Maholla.model;

import javax.persistence.*;

@Entity
@Table(name = "ApartmentFeature")
public class ApartmentFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    private Apartment Apartment;

    @ManyToOne
    private Feature Feature;

}
