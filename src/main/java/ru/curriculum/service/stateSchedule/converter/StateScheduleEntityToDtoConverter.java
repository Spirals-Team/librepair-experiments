package ru.curriculum.service.stateSchedule.converter;

import org.springframework.stereotype.Component;
import ru.curriculum.domain.stateSchedule.entity.StateProgram;
import ru.curriculum.service.stateSchedule.dto.ImplementationFormDto;
import ru.curriculum.service.stateSchedule.dto.StateProgramCreationDto;
import ru.curriculum.service.stateSchedule.dto.StateProgramViewDto;
import ru.curriculum.service.stateSchedule.dto.StudyModeDto;
import ru.curriculum.service.user.dto.UserDTO;

@Component
public class StateScheduleEntityToDtoConverter {

    public StateProgramViewDto makeViewDto(StateProgram stateProgram) {
        return StateProgramViewDto.builder()
            .name(stateProgram.name())
            .targetAudience(stateProgram.targetAudience())
            .dateStart(stateProgram.dateStart())
            .dateFinish(stateProgram.dateFinish())
            .address(stateProgram.address())
            .groupCount(stateProgram.groupCount())
            .id(stateProgram.id())
            .countOfHoursPerLerner(stateProgram.countOfHoursPerLerner())
            .lernerCount(stateProgram.lernerCount())
            .responsibleDepartment(stateProgram.responsibleDepartment())
            .mode(new StudyModeDto(stateProgram.mode().id(), stateProgram.mode().name()))
            .implementationForm(
                new ImplementationFormDto(
                    stateProgram.implementationForm().id(),
                    stateProgram.implementationForm().name()
                )
            )
            .curator(new UserDTO(stateProgram.curator()))
            .build();
    }

    public StateProgramCreationDto makeEditDto(StateProgram stateProgram) {
        return StateProgramCreationDto.builder()
                .name(stateProgram.name())
                .targetAudience(stateProgram.targetAudience())
                .dateStart(stateProgram.dateStart())
                .dateFinish(stateProgram.dateFinish())
                .address(stateProgram.address())
                .groupCount(stateProgram.groupCount())
                .id(stateProgram.id())
                .countOfHoursPerLerner(stateProgram.countOfHoursPerLerner())
                .lernerCount(stateProgram.lernerCount())
                .responsibleDepartment(stateProgram.responsibleDepartment())
                .modeId(stateProgram.mode().id())
                .implementationFormId(stateProgram.implementationForm().id())
                .curatorId(stateProgram.curator().id())
                .build();
    }
}
