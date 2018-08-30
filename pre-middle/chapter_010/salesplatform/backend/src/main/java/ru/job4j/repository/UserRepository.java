package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.model.User;

import java.util.Optional;

/**
 * User Repository.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by email
     *
     * @param email email
     * @return user
     */
    Optional<User> findByEmailIgnoreCase(String email);

    /**
     * Find user by email and password
     *
     * @param email email
     * @param passw password
     * @return user
     */
    Optional<User> findByEmailIgnoreCaseAndPassw(String email, String passw);
}
