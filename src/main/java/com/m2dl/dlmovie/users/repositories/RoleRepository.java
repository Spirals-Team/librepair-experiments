package com.m2dl.dlmovie.users.repositories;

import com.m2dl.dlmovie.users.domain.Role;
import com.m2dl.dlmovie.users.domain.RoleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
