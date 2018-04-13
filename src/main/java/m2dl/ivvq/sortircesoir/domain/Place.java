package m2dl.ivvq.sortircesoir.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1)
    private String name;

    @NotNull
    @Size(min = 1)
    private String address;

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "place")
    private List<Comment> comments;

    public Place() {
        this.comments = new ArrayList<Comment>();
    }

    public Place(String name, String address, User owner) {
        this.name = name;
        this.address = address;
        this.owner = owner;
        this.comments = new ArrayList<Comment>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
