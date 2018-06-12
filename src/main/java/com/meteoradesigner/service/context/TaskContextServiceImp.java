package com.meteoradesigner.service.context;

import com.meteoradesigner.dto.TaskContextDto;
import com.meteoradesigner.model.TaskContext;
import com.meteoradesigner.repository.DataJpaTaskContextRepository;
import com.meteoradesigner.repository.GenericAbstractCrudRepository;
import com.meteoradesigner.service.GenericAbstractCrudService;
import com.meteoradesigner.util.crud.DeleteSupport;
import com.meteoradesigner.util.mapper.EntityDtoMapper;
import com.meteoradesigner.util.mapper.TaskContextEntityDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * This @code{TaskContextServiceImp} class implements @code{TaskContextService} interface.
 */
@Service
//@Transactional(readOnly = true)
public class TaskContextServiceImp extends GenericAbstractCrudService<TaskContext,
        TaskContextDto, Integer> implements TaskContextService {

    @Autowired
    private DataJpaTaskContextRepository repository;

    @Autowired
    @Qualifier(value = "taskContextDeleteSupport")
    private DeleteSupport<Integer> deleteSupport;

    /**
     * {@inheritDoc}
     */
    @Override
    protected GenericAbstractCrudRepository<TaskContext, Integer> getRepository() {
        return repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected EntityDtoMapper<TaskContext, TaskContextDto> getEntityDtoMapper() {
        return new TaskContextEntityDtoMapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends TaskContextDto> getDtoClass() {
        return TaskContextDto.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Consumer<Integer> getDeleteSupport() {
        return deleteSupport.getDeleteSupportConsumer();
    }
}