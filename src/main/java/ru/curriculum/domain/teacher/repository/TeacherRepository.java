package ru.curriculum.domain.teacher.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.curriculum.domain.teacher.entity.Teacher;

public interface TeacherRepository extends PagingAndSortingRepository<Teacher, Integer> {

    Teacher findByUserId(Integer userId);
}
