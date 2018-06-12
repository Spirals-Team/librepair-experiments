package com.meteoradesigner.util.crud;

import com.meteoradesigner.model.Task;
import com.meteoradesigner.model.TaskContext;
import com.meteoradesigner.model.TaskPortfolio;
import com.meteoradesigner.model.User;
import com.meteoradesigner.util.exception.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * This @code{TaskDeleteSupport} class extends @code{TaskDeleteSupport} class and
 * declares the implementation.
 */
@Component
public class TaskDeleteSupport extends DeleteSupport<Integer> {

    @Override
    public Consumer<Integer> getDeleteSupportConsumer() {
        return (id) -> {
            Task currentToDelete = taskRepository.findById(id).orElseThrow(() -> new
                    NotFoundException("Task to delete not found"));

            currentToDelete.getContexts().forEach(task -> {
                TaskContext toUnbind = taskContextRepository
                        .findById(task.getId())
                        .orElseThrow(() -> new NotFoundException("TaskContext to unbind not "
                                                                         + "found"));
                toUnbind.getTasks().remove(currentToDelete);
                taskContextRepository.save(toUnbind);
            });
            currentToDelete.setContexts(null);

            TaskPortfolio portfolioToUnbind = currentToDelete.getPortfolio();
            if (portfolioToUnbind != null) {
                taskPortfolioRepository.findById(portfolioToUnbind.getId())
                                       .orElseThrow(() -> new NotFoundException(
                                               "TaskPortfolio to unbind not found"))
                                       .getTasks().remove(currentToDelete);
                taskPortfolioRepository.save(portfolioToUnbind);
                currentToDelete.setPortfolio(null);
            }

            currentToDelete.getExternalTasks().forEach(task -> {
                Task toUnbind = taskRepository
                        .findById(task.getId())
                        .orElseThrow(() -> new NotFoundException("Task to unbind not found"));
                toUnbind.getInternalTasks().remove(currentToDelete);
                taskRepository.save(toUnbind);
            });
            currentToDelete.setExternalTasks(null);

            currentToDelete.getInternalTasks().forEach(task -> {
                Task toUnbind = taskRepository
                        .findById(task.getId())
                        .orElseThrow(() -> new NotFoundException("Task to unbind not found"));
                toUnbind.getExternalTasks().remove(currentToDelete);
                taskRepository.save(toUnbind);
            });
            currentToDelete.setInternalTasks(null);

            currentToDelete.getTasksBlockedByTheTask().forEach(task -> {
                Task toUnbind = taskRepository
                        .findById(task.getId())
                        .orElseThrow(() -> new NotFoundException("Task to unbind not found"));
                toUnbind.getTasksBlockingTheTask().remove(currentToDelete);
                taskRepository.save(toUnbind);
            });
            currentToDelete.setTasksBlockedByTheTask(null);

            currentToDelete.getTasksBlockingTheTask().forEach(task -> {
                Task toUnbind = taskRepository
                        .findById(task.getId())
                        .orElseThrow(() -> new NotFoundException("Task to unbind not found"));
                toUnbind.getTasksBlockedByTheTask().remove(currentToDelete);
                taskRepository.save(toUnbind);
            });
            currentToDelete.setTasksBlockingTheTask(null);

            currentToDelete.getTasksUnlockingTheTaskRelatives().forEach(task -> {
                Task toUnbind = taskRepository
                        .findById(task.getId())
                        .orElseThrow(() -> new NotFoundException("Task to unbind not found"));
                toUnbind.getTasksWithRelativesUnlockedByTheTask().remove(currentToDelete);
                taskRepository.save(toUnbind);
            });
            currentToDelete.setTasksUnlockingTheTaskRelatives(null);

            currentToDelete.getTasksWithRelativesUnlockedByTheTask().forEach(task -> {
                Task toUnbind = taskRepository
                        .findById(task.getId())
                        .orElseThrow(() -> new NotFoundException("Task to unbind not found"));
                toUnbind.getTasksUnlockingTheTaskRelatives().remove(currentToDelete);
                taskRepository.save(toUnbind);
            });
            currentToDelete.setTasksWithRelativesUnlockedByTheTask(null);

            taskRepository.save(currentToDelete);

            User owner = userRepository
                    .findById(currentToDelete.getUser().getId())
                    .orElseThrow(() -> new NotFoundException("User to unbind not found"));
            owner.getTasks().remove(currentToDelete);

            userRepository.save(owner);
        };
    }
}