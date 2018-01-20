package ru.curriculum.domain.stateSchedule.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Форма реализации - модульная, к примеру.
 */
@Entity
@Table(name = "implementation_form")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Accessors(fluent = true)
public class ImplementationForm {

    @Id
    private String id;

    @Column(insertable = false, updatable = false)
    private String name;
}
