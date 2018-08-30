package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
