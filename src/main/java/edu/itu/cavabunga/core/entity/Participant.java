
package edu.itu.cavabunga.core.entity;

import com.fasterxml.jackson.annotation.*;
import edu.itu.cavabunga.core.entity.participant.Group;
import edu.itu.cavabunga.core.entity.participant.User;
import lombok.Data;
import org.hibernate.annotations.DiscriminatorOptions;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorOptions(force=true)
@JsonIgnoreProperties(ignoreUnknown = true, value= {"components"}) //TODO: participants with no component will give error while building JSON
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = User.class, name = "User"),
        @JsonSubTypes.Type(value = Group.class, name = "Group"),
})
@Data
public abstract class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "creation_date")
    @CreatedDate
    private Date creationDate;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    @JsonManagedReference(value = "participantAndComponent")
    @JsonIgnore
    private List<Component> components = new ArrayList<>();

    public Participant(){

    }

    public void validate(){

    }
}