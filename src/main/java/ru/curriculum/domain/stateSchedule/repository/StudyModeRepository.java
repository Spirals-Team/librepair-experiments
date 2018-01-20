package ru.curriculum.domain.stateSchedule.repository;

import org.springframework.data.repository.CrudRepository;
import ru.curriculum.domain.stateSchedule.entity.StudyMode;

public interface StudyModeRepository extends CrudRepository<StudyMode, String> {
}
