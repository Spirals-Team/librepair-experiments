package edu.itu.cavabunga.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class ComponentProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="component_id")
    private Component component;

    private boolean sharePermission;
    private boolean readPermission;
    private boolean writePermission;
    private boolean freebusyPermission;
    private boolean createPermission;

    @ElementCollection
    private List<Long> shareTo;

    @ElementCollection
    private List<Long> writeTo;

    @ElementCollection
    private List<Long> freebusyTo;

    @ElementCollection
    private List<Long> readTo;

    @ElementCollection
    private List<Long> createTo;

    public ComponentProperty() {
    }
}
