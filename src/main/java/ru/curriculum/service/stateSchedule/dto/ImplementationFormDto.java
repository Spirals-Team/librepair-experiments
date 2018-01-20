package ru.curriculum.service.stateSchedule.dto;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Форма реализации - модульная, к примеру.
 */
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@Getter
public class ImplementationFormDto {

    private String id;

    private String name;
}
