package ru.curriculum.domain.etp.entity.educationActivity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/*
 * EASection - educational activity section.
 * Раздел модуля УТП "Учебная деятельность". Характеризуется планом.
 */
@Entity
@Table(name = "education_section")
@Getter
@Accessors(fluent = true)
public class EASection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(
            mappedBy = "eaSection",
            targetEntity = EATopic.class,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private Set<EATopic> topics;
    @Setter
    @ManyToOne
    @JoinColumn(name = "education_module_id")
    private EAModule eaModule;

    public EASection() {
        this.topics = new HashSet<>();
    }

    public EASection(String name, Set<EATopic> topics) {
        this.name = name;
        this.addTopics(topics);
    }

    public EASection(Integer id, String name, Set<EATopic> topics) {
        this(name, topics);
        this.id = id;
    }

    private void addTopics(Set<EATopic> topics) {
        topics.forEach(topic -> topic.eaSection(this));
        this.topics = topics;
    }
}
