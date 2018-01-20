package ru.curriculum.domain.etp.entity.educationActivity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.curriculum.domain.etp.entity.ETP;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/*
 * EAModule - Education Activity Module.
 * Модуль УТП - "Учебная деятельность". Может содержать неограниченное кол-во разделов.
 */
@Entity
@Table(name = "education_module")
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(exclude = {"sections"})
public class EAModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(
            mappedBy = "eaModule",
            targetEntity = EASection.class,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private Set<EASection> sections;
    @ManyToOne
    @JoinColumn(name = "etp_id")
    @Setter
    private ETP etp;

    public EAModule() {
        this.sections = new HashSet<>();
    }

    public EAModule(String name, Set<EASection> sections) {
        this();
        this.name = name;
        this.addSections(sections);
    }

    public EAModule(Integer id, String name, Set<EASection> sections) {
        this(name, sections);
        this.id = id;
    }

    private void addSections(@NonNull Set<EASection> sections) {
        sections.forEach(section -> section.eaModule(this));
        this.sections = sections;
    }
}
