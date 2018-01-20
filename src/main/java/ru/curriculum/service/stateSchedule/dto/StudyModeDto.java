package ru.curriculum.service.stateSchedule.dto;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Форма обучения - очная, заочная и пр
 */
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@Getter
public class StudyModeDto {

    private String id;

    private String name;
}
