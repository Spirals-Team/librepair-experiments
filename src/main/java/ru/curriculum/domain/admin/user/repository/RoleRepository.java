package ru.curriculum.domain.admin.user.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.curriculum.domain.admin.user.entity.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, String> {
}
