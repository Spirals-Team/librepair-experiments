package edu.itu.cavabunga.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class ParticipantProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    private boolean disabled;
    private boolean readPermission;
    private boolean writePermission;
    private boolean sharePermission;
    private boolean createPermission;

    @ElementCollection
    private List<Participant> memberOf;

    public ParticipantProperty() {
    }
}
