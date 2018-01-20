package ru.curriculum.domain.stateSchedule.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.curriculum.domain.stateSchedule.entity.StateProgram;

public interface StateProgramRepository extends PagingAndSortingRepository<StateProgram, Integer> {
}
