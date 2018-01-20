package ru.curriculum.domain.etp.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.curriculum.domain.teacher.entity.Teacher;
import ru.curriculum.service.etp.dto.PlanDTO;

import javax.persistence.*;

/*
 * План в часах по определенным разделам УТП
 */
@Entity
@Table(name = "etp_plan")
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(of= {"id"})
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double lectures = 0.0;
    private Double practices = 0.0;
    private Double independentWorks = 0.0;
    private Double consultations = 0.0;
    private Double peerReviews = 0.0;
    private Double credits = 0.0;
    private Double others = 0.0;
    private Double standard = 0.0;
    private Double totalHours = 0.0;
    @ManyToOne(targetEntity = Teacher.class)
    @Setter
    private Teacher teacher;

    public Plan() {
    }

    public Plan(
            Double lectures,
            Double practices,
            Double independentWorks,
            Double consultations,
            Double peerReviews,
            Double credits,
            Double others,
            Double standard,
            Double totalHours,
            Teacher teacher
    ) {
        this.lectures = lectures;
        this.practices = practices;
        this.independentWorks = independentWorks;
        this.consultations = consultations;
        this.peerReviews = peerReviews;
        this.credits = credits;
        this.others = others;
        this.standard = standard;
        this.totalHours = totalHours;
        this.teacher = teacher;
    }

    public Plan(
            Integer id,
            Double lectures,
            Double practices,
            Double independentWorks,
            Double consultations,
            Double peerReviews,
            Double credits,
            Double others,
            Double standard,
            Double totalHours,
            Teacher teacher
    ) {
        this(
                lectures,
                practices,
                independentWorks,
                consultations,
                peerReviews,
                credits,
                others,
                standard,
                totalHours,
                teacher);
        this.id = id;
    }
}
