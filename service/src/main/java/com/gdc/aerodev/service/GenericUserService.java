package com.gdc.aerodev.service;

import com.gdc.aerodev.model.User;

public interface GenericUserService extends GenericService {

    /**
     * Inserts {@code User} into database configured by input parameters.
     *
     * @param userName name of new {@code User}
     * @param userPassword password of {@code User}
     * @param userEmail email of current {@code Project}
     * @return (0) {@param userId} of inserted {@code User}
     *         (1) or {@code null} in cause of problems
     */
    Long createUser(String userName, String userPassword, String userEmail);

    /**
     * Updates existent {@code User} chosen by {@param userId} with input parameters. If there is no need to
     * change some parameter, it should be left as empty ones.
     *
     * @param userId ID of updating {@code User}
     * @param userName new name of updating {@code User}
     * @param userPassword new password of updating {@code User}
     * @param userEmail new email for {@code User}
     * @param userLevel new user level
     * @return (0) {@param userId} of updated {@code User}
     *         (1) or {@code null} in cause of problems
     */
    Long updateUser(Long userId, String userName, String userPassword, String userEmail, short userLevel);

    User getUser(String name);

    User getUser(Long id);

}
