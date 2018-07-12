package apna.Maholla.model;

import javax.persistence.*;

@Entity
@Table(name = "ApartmentRoleFeature")
public class ApartmentRoleFeature {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    private Apartment Apartment;

    @ManyToOne
    private Roles Role;

    @ManyToOne
    private Feature Feature;
}
