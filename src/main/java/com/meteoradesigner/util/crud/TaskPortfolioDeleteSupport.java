package com.meteoradesigner.util.crud;

import com.meteoradesigner.model.Task;
import com.meteoradesigner.model.TaskPortfolio;
import com.meteoradesigner.model.User;
import com.meteoradesigner.util.exception.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * This @code{TaskPortfolioDeleteSupport} class extends @code{TaskPortfolioDeleteSupport} class and
 * declares the implementation.
 */
@Component
public class TaskPortfolioDeleteSupport extends DeleteSupport<Integer> {

    @Override
    public Consumer<Integer> getDeleteSupportConsumer() {
        return (id) -> {
            TaskPortfolio currentToDelete = taskPortfolioRepository
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException(
                            "TaskPortfolio to delete not found"));

            currentToDelete.getTasks().forEach(task -> {
                Task toUnbind = taskRepository
                        .findById(task.getId())
                        .orElseThrow(() -> new NotFoundException("Task to unbind not found"));
                toUnbind.setPortfolio(null);
                taskRepository.save(toUnbind);
            });
            currentToDelete.setTasks(null);

            taskPortfolioRepository.save(currentToDelete);

            User owner = userRepository
                    .findById((currentToDelete).getUser().getId())
                    .orElseThrow(() -> new NotFoundException("User to unbind not found"));
            owner.getPortfolios().remove(currentToDelete);
            userRepository.save(owner);
        };
    }
}
