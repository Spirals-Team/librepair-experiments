package apna.Maholla.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "ElectricBill")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class ElectricBill {

    @Id
    private int Id;

    @Column(nullable = false, unique = true)
    private int CustomerNumber;

    @ManyToOne
    private Apartment Apartment;

    @NotBlank
    private String roomNo;

    @NotBlank
    private int Amount;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;
}

