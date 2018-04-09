package io.recruiter.application.common.database.repositories;

import io.recruiter.application.common.database.model.AuthorityName;
import io.recruiter.application.common.database.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(@Param("username") String username);

    Optional<List<User>> findAllByAuthorities(AuthorityName authorityName);

    Optional<User> findByAuthoritiesAndUsername(AuthorityName authorityName, String username);
}