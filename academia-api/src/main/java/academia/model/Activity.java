package academia.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Activity {
    public Activity() {
        // hibernate needs the default constructor
    }

    public Activity(String name, String description, List<Account> trainers, List<Restriction> restrictions){
        this.name = name;
        this.description = description;
        this.trainers = trainers;
        this.restrictions = restrictions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Account> trainers;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Restriction> restrictions;

    public List<Account> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<Account> trainers) {
        this.trainers = trainers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Restriction> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<Restriction> restrictions) {
        this.restrictions = restrictions;
    }
}
