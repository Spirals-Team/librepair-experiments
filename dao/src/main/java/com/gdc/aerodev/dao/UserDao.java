package com.gdc.aerodev.dao;

import com.gdc.aerodev.model.User;

public interface UserDao extends GenericDao<User, Long> {

    /**
     * Checks inserted email on existence in database
     * @param userEmail email to check
     * @return (0) {@param userName} of {@code User} with existent email
     *         (1) {@code null} if there is no such email
     */
    String existentEmail(String userEmail);
}
