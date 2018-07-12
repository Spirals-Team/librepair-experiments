package apna.Maholla.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "ContactList")
public class ContactList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    private Apartment apartment;

    @NotBlank
    private String name;

    @NotBlank
    private String image;

    @NotBlank
    private String specialisation;

    private String description;

    @NotBlank
    private String Address1;

    @NotBlank
    private String Address2;

    @ManyToOne
    private State state;

    @ManyToOne
    private City city;

    @ManyToOne
    private Country country;

    @NotBlank
    private long pinCode;

    @NotBlank
    private long phoneNumber;

    @NotBlank
    private float rating;

    @NotBlank
    private int reviews;
}
