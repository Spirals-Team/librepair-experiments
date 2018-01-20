package ru.curriculum.domain.etp.entity.organizationMethodicalActivity;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.curriculum.domain.etp.entity.ETP;
import ru.curriculum.domain.etp.entity.Plan;

import javax.persistence.*;


@Entity
@Table(name = "organization_methodical_module")
@Getter
@Accessors(fluent = true)
public class OMAModule {
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
    @ManyToOne
    @JoinColumn(name = "etp_id")
    @Setter
    private ETP etp;

    public OMAModule() {
        this.plan = new Plan();
    }

    public OMAModule(String name, Plan plan) {
        this.name = name;
        this.plan = plan;
    }

    public OMAModule(Integer id, String name, Plan plan) {
        this(name, plan);
        this.id = id;
    }
}
