package ua.epam.spring.hometask.service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.util.exception.NotFoundException;

import java.util.Collection;

/**
 * @author Yuriy_Tkach
 */
public interface UserService extends AbstractDomainObjectService<User> {

    /**
     * Finding user by email
     * 
     * @param email
     *            Email of the user
     * @return found user or <code>null</code>
     */
    @Nullable
    User getUserByEmail(@Nonnull String email) throws NotFoundException;

    @Override
    User save(@Nonnull User object) throws NotFoundException;

    @Override
    void remove(@Nonnull User object) throws NotFoundException;

    @Override
    User getById(@Nonnull Long id) throws NotFoundException;

    @Nonnull
    @Override
    Collection<User> getAll();
}
