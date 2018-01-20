package ru.curriculum.service.stateSchedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.curriculum.domain.stateSchedule.entity.ImplementationForm;
import ru.curriculum.domain.stateSchedule.entity.StudyMode;
import ru.curriculum.domain.stateSchedule.repository.ImplementationFormRepository;
import ru.curriculum.domain.stateSchedule.repository.StudyModeRepository;
import ru.curriculum.service.stateSchedule.dto.ImplementationFormDto;
import ru.curriculum.service.stateSchedule.dto.StudyModeDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ImplementationFormFindService {

    @Autowired
    private ImplementationFormRepository implementationFormRepository;


    public Collection<ImplementationFormDto> findAll() {
        Iterable<ImplementationForm> allImplementationForms = implementationFormRepository.findAll();
        List<ImplementationFormDto> implementationFormDtos = new ArrayList<>();
        for (ImplementationForm currentMode: allImplementationForms) {
            implementationFormDtos.add(new ImplementationFormDto(currentMode.id(), currentMode.name()));
        }
        return implementationFormDtos;
    }
}
