package ru.curriculum.domain.admin.user.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.curriculum.domain.admin.user.entity.User;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    User findByUsername(String username);

    List<User> findAllByTeacherIsNull();
}
