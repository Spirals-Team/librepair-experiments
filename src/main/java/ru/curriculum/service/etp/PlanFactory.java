package ru.curriculum.service.etp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.curriculum.domain.etp.entity.Plan;
import ru.curriculum.domain.teacher.entity.Teacher;
import ru.curriculum.domain.teacher.repository.TeacherRepository;
import ru.curriculum.service.etp.dto.PlanDTO;

@Component
public class PlanFactory {
    @Autowired
    private TeacherRepository teacherRepository;

    public Plan create(PlanDTO planDTO) {
        Teacher teacher = null;
        if(null != planDTO.getTeacherId()) {
            teacher = teacherRepository.findOne(planDTO.getTeacherId());
        }
        Plan plan = new Plan(
                planDTO.getId(),
                planDTO.getLectures(),
                planDTO.getPractices(),
                planDTO.getIndependentWorks(),
                planDTO.getConsultations(),
                planDTO.getPeerReviews(),
                planDTO.getCredits(),
                planDTO.getOthers(),
                planDTO.getStandard(),
                planDTO.getTotalHours(),
                teacher
        );

        return plan;
    }
}
