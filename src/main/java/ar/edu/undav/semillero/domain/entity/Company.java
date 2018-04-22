package ar.edu.undav.semillero.domain.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "company")
public class Company extends AbstractPersistable<Long> {

    private String name;
    private String contact;
    private String email;
    private String comments;

    @OneToMany(mappedBy = "company")
    private List<Interview> interviews = new ArrayList<>();

    public Company() {
    }

    public Company(String name, String contact, String email, String comments) {
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void addInterview(Interview interview) {
        interviews.add(interview);
    }

    public List<Interview> getInterviews() {
        return Collections.unmodifiableList(interviews);
    }
}
