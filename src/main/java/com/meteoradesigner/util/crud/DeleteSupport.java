package com.meteoradesigner.util.crud;

import com.meteoradesigner.model.Task;
import com.meteoradesigner.model.TaskContext;
import com.meteoradesigner.model.TaskPortfolio;
import com.meteoradesigner.model.User;
import com.meteoradesigner.repository.GenericAbstractCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * This @code{DeleteSupport} class states behaviour for delete operations.
 */
@Component
public abstract class DeleteSupport<ID> {

    @Autowired
    protected GenericAbstractCrudRepository<User,Integer> userRepository;
    @Autowired
    protected GenericAbstractCrudRepository<TaskContext,Integer> taskContextRepository;
    @Autowired
    protected GenericAbstractCrudRepository<TaskPortfolio,Integer> taskPortfolioRepository;
    @Autowired
    protected GenericAbstractCrudRepository<Task,Integer> taskRepository;

    public abstract Consumer<ID> getDeleteSupportConsumer();
}
