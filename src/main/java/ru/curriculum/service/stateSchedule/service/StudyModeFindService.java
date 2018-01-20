package ru.curriculum.service.stateSchedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import ru.curriculum.domain.stateSchedule.entity.StudyMode;
import ru.curriculum.domain.stateSchedule.repository.StudyModeRepository;
import ru.curriculum.service.stateSchedule.dto.StudyModeDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class StudyModeFindService {

    @Autowired
    private StudyModeRepository studyModeRepository;


    public Collection<StudyModeDto> findAll() {
        Iterable<StudyMode> allStudyModes = studyModeRepository.findAll();
        List<StudyModeDto> studyModeDtos = new ArrayList<>();
        for (StudyMode currentMode: allStudyModes) {
            studyModeDtos.add(new StudyModeDto(currentMode.id(), currentMode.name()));
        }

        return studyModeDtos;
    }
}
