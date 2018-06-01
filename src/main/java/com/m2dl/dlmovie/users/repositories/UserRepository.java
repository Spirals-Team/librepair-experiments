package com.m2dl.dlmovie.users.repositories;

import com.m2dl.dlmovie.users.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByPseudoOrEmail(String pseudo, String email);

    Optional<User> findById(Long id);
}
