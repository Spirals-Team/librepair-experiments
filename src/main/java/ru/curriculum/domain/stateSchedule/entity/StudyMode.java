package ru.curriculum.domain.stateSchedule.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Форма обучения - очная, заочная и пр
 */
@Entity
@Table(name = "study_mode")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Accessors(fluent = true)
public class StudyMode {

    @Id
    private String id;

    @Column(insertable = false, updatable = false)
    private String name;
}
