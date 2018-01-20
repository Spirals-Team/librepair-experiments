package ru.curriculum.service.etp.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import ru.curriculum.domain.etp.entity.educationMethodicalActivity.EMAModule;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EMAModuleDTO {
    private Integer id;
    @NotEmpty(message = "\"Учебная-методическая деятельность\" необходимо заполнить поле \"Название модуля\"")
    private String name;
    private PlanDTO plan;

    public EMAModuleDTO() {
        this.plan = new PlanDTO();
    }

    public EMAModuleDTO(EMAModule module) {
        this.id = module.id();
        this.name = module.name();
        this.plan = new PlanDTO(module.plan());
    }
}
