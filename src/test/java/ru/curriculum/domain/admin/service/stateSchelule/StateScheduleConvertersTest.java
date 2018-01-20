package ru.curriculum.domain.admin.service.stateSchelule;

import boot.IntegrationBoot;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.curriculum.domain.admin.user.entity.User;
import ru.curriculum.domain.helper.UserTestFactory;
import ru.curriculum.domain.stateSchedule.entity.StateProgram;
import ru.curriculum.service.stateSchedule.converter.DtoToStateScheduleConverter;
import ru.curriculum.service.stateSchedule.converter.StateScheduleEntityToDtoConverter;
import ru.curriculum.service.stateSchedule.dto.StateProgramCreationDto;
import ru.curriculum.service.stateSchedule.dto.StateProgramViewDto;

import java.util.Date;

public class StateScheduleConvertersTest extends IntegrationBoot{

    @Autowired
    private DtoToStateScheduleConverter dtoToStateScheduleConverter;

    @Autowired
    private StateScheduleEntityToDtoConverter stateScheduleEntityToDtoConverter;

    @Autowired
    private UserTestFactory userTestFactory;

    @Test
    public void whenDtoConvertsToEntityAndToDtoThenDTOsTheSame() throws Exception {
        User curator = userTestFactory.createAndSaveRandomUser();

        StateProgramCreationDto newStateProgram = StateProgramCreationDto.builder()
            .address("Kazan, main street 1")
            .countOfHoursPerLerner(10)
            .curatorId(curator.id())
            .dateStart(new Date())
            .dateFinish(new Date())
            .groupCount(2)
            .implementationFormId("modular")
            .lernerCount(10)
            .modeId("fulltime")
            .name("some name")
            .responsibleDepartment("some department")
            .targetAudience("teachers")
            .build();

        StateProgram stateProgramEntity = dtoToStateScheduleConverter.createBasedOnDto(newStateProgram);

        StateProgramViewDto viewDto = stateScheduleEntityToDtoConverter.makeViewDto(stateProgramEntity);
        Assert.assertEquals(stateProgramEntity.id(), viewDto.getId());
        Assert.assertEquals(newStateProgram.getAddress(), viewDto.getAddress());
        Assert.assertEquals(newStateProgram.getCountOfHoursPerLerner(), viewDto.getCountOfHoursPerLerner());
        Assert.assertEquals(newStateProgram.getCuratorId(), viewDto.getCurator().getId());
        Assert.assertEquals(stateProgramEntity.curator().username(), viewDto.getCurator().getUsername());
        Assert.assertEquals(newStateProgram.getDateStart(), viewDto.getDateStart());
        Assert.assertEquals(newStateProgram.getDateFinish(), viewDto.getDateFinish());
        Assert.assertEquals(newStateProgram.getGroupCount(), viewDto.getGroupCount());
        Assert.assertEquals(newStateProgram.getImplementationFormId(), viewDto.getImplementationForm().getId());
        Assert.assertEquals(stateProgramEntity.implementationForm().name(), viewDto.getImplementationForm().getName());
        Assert.assertEquals(newStateProgram.getLernerCount(), viewDto.getLernerCount());
        Assert.assertEquals(newStateProgram.getModeId(), viewDto.getMode().getId());
        Assert.assertEquals(stateProgramEntity.mode().name(), viewDto.getMode().getName());
        Assert.assertEquals(newStateProgram.getName(), viewDto.getName());
        Assert.assertEquals(newStateProgram.getResponsibleDepartment(), viewDto.getResponsibleDepartment());
        Assert.assertEquals(newStateProgram.getTargetAudience(), viewDto.getTargetAudience());
    }
}
