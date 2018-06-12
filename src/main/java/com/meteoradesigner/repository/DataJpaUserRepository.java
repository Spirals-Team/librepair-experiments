package com.meteoradesigner.repository;

import com.meteoradesigner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * This interface @code{DataJpaUserRepository} declares the data jpa @code{user} repository.
 */
@Transactional
@Repository
public interface DataJpaUserRepository extends JpaRepository<User, Integer>, GenericAbstractCrudRepository<User,
        Integer> {

    /**
     * Finds user by mail.
     *
     * @param mail given.
     * @return @code{User} by mail.
     */
    User findByEmail(String mail);

    /**
     * Finds user by display name.
     *
     * @param displayName given.
     * @return @code{User} by displayName.
     */
    User findByDisplayName(String displayName);
}