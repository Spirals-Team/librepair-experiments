package com.simplenotesapp.simplenotesapp.repository;

import com.simplenotesapp.simplenotesapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneById(final Long id);

    Optional<User> findOneByLogin(final String login);

    Set<User> findAllByName(final String name);

    Set<User> findAllBySurname(final String surname);
}