package ru.curriculum.service.stateSchedule.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.curriculum.domain.admin.user.entity.User;
import ru.curriculum.domain.admin.user.repository.UserRepository;
import ru.curriculum.domain.stateSchedule.entity.ImplementationForm;
import ru.curriculum.domain.stateSchedule.entity.StateProgram;
import ru.curriculum.domain.stateSchedule.entity.StudyMode;
import ru.curriculum.domain.stateSchedule.repository.ImplementationFormRepository;
import ru.curriculum.domain.stateSchedule.repository.StudyModeRepository;
import ru.curriculum.service.stateSchedule.dto.StateProgramCreationDto;

@Component
public class DtoToStateScheduleConverter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyModeRepository studyModeRepository;

    @Autowired
    private ImplementationFormRepository implementationFormRepository;

    public StateProgram createBasedOnDto(StateProgramCreationDto stateProgramCreationDto) {

        User curator = userRepository.findOne(stateProgramCreationDto.getCuratorId());
        ImplementationForm implementationForm = implementationFormRepository.findOne(stateProgramCreationDto.getImplementationFormId());
        StudyMode studyMode = studyModeRepository.findOne(stateProgramCreationDto.getModeId());

        StateProgram newStateProgram = StateProgram.builder()
            .name(stateProgramCreationDto.getName())
            .curator(curator)
            .mode(studyMode)
            .implementationForm(implementationForm)
            .dateStart(stateProgramCreationDto.getDateStart())
            .dateFinish(stateProgramCreationDto.getDateFinish())
            .lernerCount(stateProgramCreationDto.getLernerCount())
            .groupCount(stateProgramCreationDto.getGroupCount())
            .countOfHoursPerLerner(stateProgramCreationDto.getCountOfHoursPerLerner())
            .responsibleDepartment(stateProgramCreationDto.getResponsibleDepartment())
            .address(stateProgramCreationDto.getAddress())
            .targetAudience(stateProgramCreationDto.getTargetAudience())
            .build();

        return newStateProgram;
    }
}
