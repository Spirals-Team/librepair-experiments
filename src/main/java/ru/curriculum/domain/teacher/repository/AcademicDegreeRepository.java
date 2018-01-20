package ru.curriculum.domain.teacher.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.curriculum.domain.teacher.entity.AcademicDegree;

public interface AcademicDegreeRepository extends PagingAndSortingRepository<AcademicDegree, String> {
}
