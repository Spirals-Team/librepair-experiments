package edu.itu.cavabunga.core.entity;

import javax.persistence.*;
import java.util.List;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isReadPermission() {
        return readPermission;
    }

    public void setReadPermission(boolean readPermission) {
        this.readPermission = readPermission;
    }

    public boolean isWritePermission() {
        return writePermission;
    }

    public void setWritePermission(boolean writePermission) {
        this.writePermission = writePermission;
    }

    public boolean isSharePermission() {
        return sharePermission;
    }

    public void setSharePermission(boolean sharePermission) {
        this.sharePermission = sharePermission;
    }

    public boolean isCreatePermission() {
        return createPermission;
    }

    public void setCreatePermission(boolean createPermission) {
        this.createPermission = createPermission;
    }

    public List<Participant> getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(List<Participant> memberOf) {
        this.memberOf = memberOf;
    }
}
