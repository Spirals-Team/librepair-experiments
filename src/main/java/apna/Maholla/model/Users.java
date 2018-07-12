package apna.Maholla.model;

import apna.Maholla.Miscellaneous.EncryptAndDecrypt;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Users")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(nullable = false, unique = true)
    public String userid;

    @NotBlank
    @Column(nullable = false, unique = true)
    public String emailid;

    @Column(unique = true)
    public long phonenumber;

    public String image;

    @NotBlank
    public String password;

    @NotBlank
    public String name;

    public String block;

    @NotBlank
    public String flatnumber;

    public int role;

    @Column(nullable = false)
    public int apartmentkey;


    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdat;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedat;

    public void setPassword() throws Exception {
        password = EncryptAndDecrypt.encrypt(this.password);
    }

    public int getId() {
        return id;
    }
}

