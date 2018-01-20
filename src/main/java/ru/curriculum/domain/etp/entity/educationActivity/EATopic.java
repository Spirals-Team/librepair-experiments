package ru.curriculum.domain.etp.entity.educationActivity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.curriculum.domain.etp.entity.Plan;

import javax.persistence.*;

@Entity
@Table(name = "education_topic")
@Getter
@Accessors(fluent = true)
public class EATopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne(
            targetEntity = Plan.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "etp_plan_id")
    private Plan plan;
    @Setter
    @ManyToOne
    @JoinColumn(name = "education_section_id")
    private EASection eaSection;

    public EATopic() {
        this.plan = new Plan();
    }

    public EATopic(String name, Plan plan) {
        this.name = name;
        this.plan = plan;
    }

    public EATopic(Integer id, String name, Plan plan) {
        this(name, plan);
        this.id = id;
    }
}
