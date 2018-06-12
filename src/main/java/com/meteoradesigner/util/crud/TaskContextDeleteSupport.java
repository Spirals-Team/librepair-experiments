package com.meteoradesigner.util.crud;

import com.meteoradesigner.model.Task;
import com.meteoradesigner.model.TaskContext;
import com.meteoradesigner.model.User;
import com.meteoradesigner.util.exception.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * This @code{TaskContextDeleteSupport} class extends @code{TaskContextDeleteSupport} class and
 * declares the implementation.
 */
@Component
public class TaskContextDeleteSupport extends DeleteSupport<Integer> {

    @Override
    public Consumer<Integer> getDeleteSupportConsumer() {
        return (id) -> {
            TaskContext currentToDelete = taskContextRepository
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException(
                            "TaskContext to delete not found"));
            currentToDelete.getExternalContexts().forEach(task -> {
                TaskContext toUnbind = taskContextRepository
                        .findById(task.getId())
                        .orElseThrow(() -> new NotFoundException(
                                "TaskContext to unbind not found"));
                toUnbind.getInternalContexts().remove(currentToDelete);
                taskContextRepository.save(toUnbind);
            });

            currentToDelete.setExternalContexts(null);

            currentToDelete.getInternalContexts()
                           .forEach(task -> {
                               TaskContext toUnbind = taskContextRepository
                                       .findById(task.getId())
                                       .orElseThrow(() -> new NotFoundException(
                                               "TaskContext to unbind not found"));
                               toUnbind.getExternalContexts().remove(currentToDelete);
                               taskContextRepository.save(toUnbind);
                           });
            currentToDelete.setInternalContexts(null);

            currentToDelete.getTasks().forEach(task -> {
                Task toUnbind = taskRepository
                        .findById(task.getId())
                        .orElseThrow(() -> new NotFoundException("Task to unbind not found"));
                toUnbind.getContexts().remove(currentToDelete);
                taskRepository.save(toUnbind);
            });
            currentToDelete.setTasks(null);

            taskContextRepository.save(currentToDelete);

            User owner = userRepository
                    .findById(currentToDelete.getUser().getId())
                    .orElseThrow(() -> new NotFoundException("User to unbind not found"));
            owner.getContexts().remove(currentToDelete);
            userRepository.save(owner);
        };
    }
}