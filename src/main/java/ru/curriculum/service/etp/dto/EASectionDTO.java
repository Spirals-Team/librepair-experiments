package ru.curriculum.service.etp.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import ru.curriculum.domain.etp.entity.educationActivity.EASection;
import ru.curriculum.domain.etp.entity.educationActivity.EATopic;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class EASectionDTO {
    private Integer id;
    @NotEmpty(message = "\"Учебно деятельность\" необходимо заполнить поле \"Название раздела\"")
    private String name;
    @Valid
    private List<EATopicDTO> topics;

    public EASectionDTO() {
        this.topics = new ArrayList<>();
    }

    public EASectionDTO(EASection section) {
        this();
        this.id = section.id();
        this.name = section.name();
        this.topics = section.topics()
                .stream()
                .sorted(Comparator.comparing(EATopic::id))
                .map(EATopicDTO::new)
                .collect(Collectors.toList());
    }
}
