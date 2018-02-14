package ro.msg.cm.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ro.msg.cm.types.CandidateCheck;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "candidate_not_validated")
public class CandidateNotValidated {
    private @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "education_status")
    private String educationStatus;
    @Column(name = "original_study_year")
    private int originalStudyYear;
    @Column(name = "event")
    private String event;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfAdding;
    private @ManyToOne
    @JoinColumn(name = "education_id")
    Education education;
}