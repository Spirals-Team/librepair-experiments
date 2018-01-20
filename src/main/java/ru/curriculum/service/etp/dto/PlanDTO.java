package ru.curriculum.service.etp.dto;

import lombok.Getter;
import lombok.Setter;
import ru.curriculum.domain.etp.entity.Plan;
import ru.curriculum.domain.teacher.entity.Teacher;
import ru.curriculum.service.teacher.dto.TeacherDTO;

@Getter
@Setter
public class PlanDTO {
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
    private TeacherDTO teacher;
    private Teacher domainTeacher;
    private Integer teacherId;

    public PlanDTO() {}

    public PlanDTO(Plan plan) {
        this.id = plan.id();
        this.lectures = plan.lectures();
        this.practices = plan.practices();
        this.independentWorks = plan.independentWorks();
        this.consultations = plan.consultations();
        this.peerReviews = plan.peerReviews();
        this.credits = plan.credits();
        this.others = plan.others();
        this.standard = plan.totalHours();
        this.totalHours = plan.totalHours();
        this.teacher = null != plan.teacher() ? new TeacherDTO(plan.teacher()) : null;
        this.teacherId = null != plan.teacher() ? plan.teacher().id() : null;
    }
}
