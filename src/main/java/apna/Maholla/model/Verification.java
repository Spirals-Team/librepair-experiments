package apna.Maholla.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Verification")
public class Verification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public String userid;

    @org.hibernate.annotations.ColumnDefault("false")
    public boolean apartment;

    @org.hibernate.annotations.ColumnDefault("false")
    public boolean emailid;

    @org.hibernate.annotations.ColumnDefault("false")
    public boolean phonenumber;
}
