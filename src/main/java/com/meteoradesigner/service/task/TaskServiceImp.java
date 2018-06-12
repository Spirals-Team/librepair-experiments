package com.meteoradesigner.service.task;

import com.meteoradesigner.dto.TaskDto;
import com.meteoradesigner.model.Task;
import com.meteoradesigner.repository.DataJpaTaskRepository;
import com.meteoradesigner.repository.GenericAbstractCrudRepository;
import com.meteoradesigner.service.GenericAbstractCrudService;
import com.meteoradesigner.util.crud.DeleteSupport;
import com.meteoradesigner.util.mapper.EntityDtoMapper;
import com.meteoradesigner.util.mapper.TaskEntityDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * This @code{TaskServiceImp} class implements @code{TaskService} interface.
 */
@Service
//@Transactional(readOnly = true)
public class TaskServiceImp extends GenericAbstractCrudService<Task, TaskDto, Integer> implements
                                                                                       TaskService {

    @Autowired
    private DataJpaTaskRepository repository;

    @Autowired
    @Qualifier(value = "taskDeleteSupport")
    private DeleteSupport<Integer> deleteSupport;

    /**
     * {@inheritDoc}
     */
    @Override
    protected GenericAbstractCrudRepository<Task, Integer> getRepository() {
        return repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected EntityDtoMapper<Task, TaskDto> getEntityDtoMapper() {
        return new TaskEntityDtoMapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends TaskDto> getDtoClass() {
        return TaskDto.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Consumer<Integer> getDeleteSupport() {
        return deleteSupport.getDeleteSupportConsumer();
    }
}
