package apna.Maholla.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "Notice")
public class Notice {
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
    private Date Date;

    @NotBlank
    private String Name;

    @NotBlank
    private String Designation;

}
