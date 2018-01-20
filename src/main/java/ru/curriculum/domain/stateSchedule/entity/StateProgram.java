package ru.curriculum.domain.stateSchedule.entity;

import lombok.*;
import lombok.experimental.Accessors;
import ru.curriculum.domain.admin.user.entity.User;
import ru.curriculum.domain.teacher.entity.Teacher;

import javax.persistence.*;
import java.util.Date;

/**
 * Государственная программа, на основании которой формируется УТП
 */
@Entity
@Table(name = "state_program")
@Data
@Accessors(fluent = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StateProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String targetAudience;

    private String name;

    @OneToOne(targetEntity = StudyMode.class, fetch = FetchType.EAGER)
    private StudyMode mode;

    @OneToOne(targetEntity = ImplementationForm.class, fetch = FetchType.EAGER)
    private ImplementationForm implementationForm;

    private Integer lernerCount;

    private Integer groupCount;

    private Integer countOfHoursPerLerner;

    private Date dateStart;

    private Date dateFinish;

    @Column(length = 1024)
    private String responsibleDepartment;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User curator;

    private String address;
}
