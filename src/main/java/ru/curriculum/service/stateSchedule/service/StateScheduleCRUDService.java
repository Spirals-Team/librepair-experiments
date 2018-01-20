package ru.curriculum.service.stateSchedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.curriculum.domain.stateSchedule.entity.StateProgram;
import ru.curriculum.domain.stateSchedule.repository.StateProgramRepository;
import ru.curriculum.service.stateSchedule.converter.DtoToStateScheduleConverter;
import ru.curriculum.service.stateSchedule.converter.StateScheduleEntityToDtoConverter;
import ru.curriculum.service.stateSchedule.dto.StateProgramCreationDto;
import ru.curriculum.service.stateSchedule.dto.StateProgramViewDto;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class StateScheduleCRUDService {

    @Autowired
    private StateProgramRepository stateProgramRepository;

    @Autowired
    private DtoToStateScheduleConverter dtoToStateScheduleConverter;

    @Autowired
    private StateScheduleEntityToDtoConverter stateScheduleEntityToDtoConverter;

    @Autowired
    private ImplementationFormFindService implementationFormFindService;

    @Autowired
    private StudyModeFindService studyModeFindService;

    public void create(StateProgramCreationDto stateProgramCreationDto) {
        stateProgramRepository.save(dtoToStateScheduleConverter.createBasedOnDto(stateProgramCreationDto));
    }

    public StateProgramViewDto get(Integer id) {
        StateProgram program = stateProgramRepository.findOne(id);
        return stateScheduleEntityToDtoConverter.makeViewDto(program);
    }

    public StateProgramCreationDto getCreationDto(Integer id) {
        StateProgram program = stateProgramRepository.findOne(id);
        return stateScheduleEntityToDtoConverter.makeEditDto(program);
    }

    public Collection<StateProgramViewDto> findAll() {
        Iterable<StateProgram> programs = stateProgramRepository.findAll();

        Collection<StateProgramViewDto> programsDto = new ArrayList<>();
        for (StateProgram currentStateProgram: programs) {
            programsDto.add(stateScheduleEntityToDtoConverter.makeViewDto(currentStateProgram));
        }
        return programsDto;
    }

    public void delete(Integer id) {
        stateProgramRepository.delete(id);
    }
}
