package ru.curriculum.service.etp.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import ru.curriculum.domain.etp.entity.educationActivity.EATopic;

@Getter
@Setter
public class EATopicDTO {
    private Integer id;
    @NotEmpty(message = "\"Учебно деятельность\" необходимо заполнить поле \"Название темы\"")
    private String name;
    private PlanDTO plan;

    public EATopicDTO() {
        this.plan = new PlanDTO();
    }

    public EATopicDTO(EATopic topic) {
        this.id = topic.id();
        this.name = topic.name();
        this.plan = new PlanDTO(topic.plan());
    }
}
