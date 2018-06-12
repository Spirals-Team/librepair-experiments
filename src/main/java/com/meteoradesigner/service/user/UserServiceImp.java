package com.meteoradesigner.service.user;

import com.meteoradesigner.dto.UserDto;
import com.meteoradesigner.model.User;
import com.meteoradesigner.repository.DataJpaUserRepository;
import com.meteoradesigner.repository.GenericAbstractCrudRepository;
import com.meteoradesigner.service.GenericAbstractCrudService;
import com.meteoradesigner.util.crud.DeleteSupport;
import com.meteoradesigner.util.mapper.EntityDtoMapper;
import com.meteoradesigner.util.mapper.UserEntityDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * This @code{UserServiceImp} class implements @code{UserService} interface.
 */
@Service
//@Transactional(readOnly = true)
public class UserServiceImp extends GenericAbstractCrudService<User, UserDto, Integer> implements
                                                                                       UserService {

    @Autowired
    private DataJpaUserRepository repository;

    @Autowired
    @Qualifier(value = "userDeleteSupport")
    private DeleteSupport<Integer> deleteSupport;

    /**
     * {@inheritDoc}
     */
    @Override
    protected GenericAbstractCrudRepository<User, Integer> getRepository() {
        return repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected EntityDtoMapper<User, UserDto> getEntityDtoMapper() {
        return new UserEntityDtoMapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends UserDto> getDtoClass() {
        return UserDto.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Consumer<Integer> getDeleteSupport() {
        return deleteSupport.getDeleteSupportConsumer();
    }
}
