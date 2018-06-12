package data;

import com.meteoradesigner.HasId;
import com.meteoradesigner.model.Task;
import com.meteoradesigner.model.TaskContext;
import com.meteoradesigner.model.TaskPortfolio;
import com.meteoradesigner.model.User;
import com.meteoradesigner.repository.GenericAbstractCrudRepository;
import com.meteoradesigner.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static java.lang.System.lineSeparator;
import static org.junit.Assert.assertEquals;

//TODO documentation.
public abstract class GenericDataJpaRepositoryTestData<E extends HasId> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericDataJpaRepositoryTestData.class);

    public abstract Collection<E[]> getSaveOneTestData();

    public abstract Collection<E[]> getFindOneTestData();

    public abstract Collection<E[]> getDeleteOneTestData();

    public abstract List<E> getFindAllTestData();

    @SuppressWarnings("unchecked")
    public static <E extends HasId, ID extends Serializable> BiConsumer<E[],
            GenericAbstractCrudRepository<E, ID>> getSaveOneTestConsumer() {
        final BiConsumer<E[], GenericAbstractCrudRepository<E, ID>> consumer;
        consumer = (ar, rep) -> {
            E expected = ar[1];
            E toSave = ar[0];
            LOGGER.info(String.format("Expected, debugging%s", expected));
            LOGGER.info(String.format("toSave, debugging%s", toSave));
            E actual = rep.save(toSave);
            LOGGER.info(String.format("Actual, debugging%s", actual), actual);
            E fromDb = rep.findById((ID) expected.getId()).orElseThrow(() -> new
                    NotFoundException("Entity to save not found"));
            LOGGER.info(String.format("From DB, debugging%s", fromDb), fromDb);

            assertEquals(
                    String.format("SaveOne saveOneTest failed:" + lineSeparator() + " expected=%s" +
                            lineSeparator() + " actual= %s", expected, actual),
                    expected,
                    actual);
        };
        return consumer;
    }

    @SuppressWarnings("unchecked")
    public static <E extends HasId,
            ID extends Serializable> BiConsumer<E[],
            GenericAbstractCrudRepository<E, ID>> getFindOneTestConsumer() {
        final BiConsumer<E[], GenericAbstractCrudRepository<E, ID>> consumer;
        consumer = (ar, rep) -> {
            E expected = ar[1];
            E toFind = ar[0];
            LOGGER.info(String.format("Expected=%s", expected), expected);
            LOGGER.info(String.format("toFind=%s", toFind), toFind);
            E actual = rep.findById((ID) toFind.getId()).orElseThrow(() -> new
                    NotFoundException("Entity to find not found"));
            LOGGER.info(String.format("Actual, debugging%s", actual), actual);
            assertEquals(
                    String.format("FindOne saveOneTest failed:" + lineSeparator() + " expected=%s" +
                            lineSeparator() + " actual= %s", expected, actual),
                    expected,
                    actual);
        };
        return consumer;
    }

    private static void getDeleteOneTaskTestConsumerSupport(GenericAbstractCrudRepository<Task, Integer> taskRepository,
                                                            GenericAbstractCrudRepository<User, Integer> userRepository,
                                                            GenericAbstractCrudRepository<TaskContext, Integer>
                                                                    taskContextRepository,
                                                            GenericAbstractCrudRepository<TaskPortfolio, Integer>
                                                                    taskPortfolioRepository,
                                                            Task currentToDelete,
                                                            Task expected
    ) {

        currentToDelete.getContexts().forEach(task -> {
            TaskContext toUnbind = taskContextRepository.findById(task.getId()).orElseThrow(() -> new
                    NotFoundException("TaskContext to unbind not found"));
            toUnbind.getTasks().remove(currentToDelete);
            taskContextRepository.save(toUnbind);
        });
        currentToDelete.setContexts(null);

        TaskPortfolio portfolioToUnbind = currentToDelete.getPortfolio();
        if (portfolioToUnbind != null) {
            taskPortfolioRepository.findById(portfolioToUnbind.getId())
                    .orElseThrow(() -> new NotFoundException("TaskPortfolio to unbind not found"))
                    .getTasks().remove(currentToDelete);
            taskPortfolioRepository.save(portfolioToUnbind);
            currentToDelete.setPortfolio(null);
        }

        currentToDelete.getExternalTasks().forEach(task -> {
            Task toUnbind = taskRepository.findById(task.getId()).orElseThrow(() -> new
                    NotFoundException("Task to unbind not found"));
            toUnbind.getInternalTasks().remove(currentToDelete);
            taskRepository.save(toUnbind);
        });
        currentToDelete.setExternalTasks(null);

        currentToDelete.getInternalTasks().forEach(task -> {
            Task toUnbind = taskRepository.findById(task.getId()).orElseThrow(() -> new
                    NotFoundException("Task to unbind not found"));
            toUnbind.getExternalTasks().remove(currentToDelete);
            taskRepository.save(toUnbind);
        });
        currentToDelete.setInternalTasks(null);

        currentToDelete.getTasksBlockedByTheTask().forEach(task -> {
            Task toUnbind = taskRepository.findById(task.getId()).orElseThrow(() -> new
                    NotFoundException("Task to unbind not found"));
            toUnbind.getTasksBlockingTheTask().remove(currentToDelete);
            taskRepository.save(toUnbind);
        });
        currentToDelete.setTasksBlockedByTheTask(null);

        currentToDelete.getTasksBlockingTheTask().forEach(task -> {
            Task toUnbind = taskRepository.findById(task.getId()).orElseThrow(() -> new
                    NotFoundException("Task to unbind not found"));
            toUnbind.getTasksBlockedByTheTask().remove(currentToDelete);
            taskRepository.save(toUnbind);
        });
        currentToDelete.setTasksBlockingTheTask(null);

        currentToDelete.getTasksUnlockingTheTaskRelatives().forEach(task -> {
            Task toUnbind = taskRepository.findById(task.getId()).orElseThrow(() -> new
                    NotFoundException("Task to unbind not found"));
            toUnbind.getTasksWithRelativesUnlockedByTheTask().remove(currentToDelete);
            taskRepository.save(toUnbind);
        });
        currentToDelete.setTasksUnlockingTheTaskRelatives(null);

        currentToDelete.getTasksWithRelativesUnlockedByTheTask().forEach(task -> {
            Task toUnbind = taskRepository.findById(task.getId()).orElseThrow(() -> new
                    NotFoundException("Task to unbind not found"));
            toUnbind.getTasksUnlockingTheTaskRelatives().remove(currentToDelete);
            taskRepository.save(toUnbind);
        });
        currentToDelete.setTasksWithRelativesUnlockedByTheTask(null);

        taskRepository.save(currentToDelete);

        User owner = userRepository.findById(currentToDelete.getUser().getId()).orElseThrow(() -> new
                NotFoundException("User to unbind not found"));
        owner.getTasks().remove(currentToDelete);
        userRepository.save(owner);

        Task actual = taskRepository.findById(currentToDelete.getId()).orElse(null);
        LOGGER.info(String.format("Actual, debugging%s", actual), actual);
        assertEquals(
                String.format("DeleteOne failed:" + lineSeparator()
                                + "expected=%s" + lineSeparator()
                                + " actual= %s", expected,
                        actual),
                expected,
                actual);
    }

    private static void getDeleteOneTaskContextTestConsumerSupport(GenericAbstractCrudRepository<TaskContext, Integer>
                                                                           taskContextRepository,
                                                                   GenericAbstractCrudRepository<User, Integer>
                                                                           userRepository,
                                                                   GenericAbstractCrudRepository<Task, Integer>
                                                                           taskRepository,
                                                                   TaskContext currentToDelete,
                                                                   TaskContext expected
    ) {
        currentToDelete.getExternalContexts().forEach(task -> {
            TaskContext toUnbind = taskContextRepository.findById(task.getId()).orElseThrow(() -> new
                    NotFoundException("TaskContext to unbind not found"));
            toUnbind.getInternalContexts().remove(currentToDelete);
            taskContextRepository.save(toUnbind);
        });
        currentToDelete.setExternalContexts(null);

        currentToDelete.getInternalContexts().forEach(task -> {
            TaskContext toUnbind = taskContextRepository.findById(task.getId()).orElseThrow(() -> new
                    NotFoundException("TaskContext to unbind not found"));
            toUnbind.getExternalContexts().remove(currentToDelete);
            taskContextRepository.save(toUnbind);
        });
        currentToDelete.setInternalContexts(null);

        currentToDelete.getTasks().forEach(task -> {
            Task toUnbind = taskRepository.findById(task.getId()).orElseThrow(() -> new
                    NotFoundException("Task to unbind not found"));
            toUnbind.getContexts().remove(currentToDelete);
            taskRepository.save(toUnbind);
        });
        currentToDelete.setTasks(null);

        taskContextRepository.save(currentToDelete);

        User owner = userRepository.findById(currentToDelete.getUser().getId()).orElseThrow(() -> new
                NotFoundException("User to unbind not found"));
        owner.getContexts().remove(currentToDelete);
        userRepository.save(owner);

        TaskContext actual = taskContextRepository.findById(currentToDelete.getId()).orElse(null);
        LOGGER.info(String.format("Actual, debugging%s", actual), actual);
        assertEquals(
                String.format("DeleteOne failed:" + lineSeparator()
                                + "expected=%s" + lineSeparator()
                                + " actual= %s", expected,
                        actual),
                expected,
                actual);
    }

    private static void getDeleteOneTaskPortfolioTestConsumerSupport(GenericAbstractCrudRepository<TaskPortfolio,
            Integer>
                                                                             taskPortfolioRepository,
                                                                     GenericAbstractCrudRepository<User, Integer>
                                                                             userRepository,
                                                                     GenericAbstractCrudRepository<Task, Integer>
                                                                             taskRepository,
                                                                     TaskPortfolio currentToDelete,
                                                                     TaskPortfolio expected
    ) {
        currentToDelete.getTasks().forEach(task -> {
            Task toUnbind = taskRepository.findById(task.getId()).orElseThrow(() -> new
                    NotFoundException("Task to unbind not found"));
            toUnbind.setPortfolio(null);
            taskRepository.save(toUnbind);
        });
        currentToDelete.setTasks(null);

        taskPortfolioRepository.save(currentToDelete);

        User owner = userRepository.findById((currentToDelete).getUser().getId()).orElseThrow(() -> new
                NotFoundException("User to unbind not found"));
        owner.getPortfolios().remove(currentToDelete);
        userRepository.save(owner);

        TaskPortfolio actual = taskPortfolioRepository.findById(currentToDelete.getId()).orElse(null);
        LOGGER.info(String.format("Actual, debugging%s", actual), actual);
        assertEquals(
                String.format("DeleteOne failed:" + lineSeparator()
                                + "expected=%s" + lineSeparator()
                                + " actual= %s", expected,
                        actual),
                expected,
                actual);
    }

    private static void getDeleteOneUserTestConsumerSupport(GenericAbstractCrudRepository<User, Integer> userRepository,
                                                            User currentToDelete,
                                                            User expected
    ) {
        userRepository.deleteById(currentToDelete.getId());
        User actual = userRepository.findById((currentToDelete.getId())).orElse(null);
        LOGGER.info(String.format("Actual, debugging%s", actual), actual);
        assertEquals(
                String.format("DeleteOne cruTest failed:" + lineSeparator()
                        + "expected=%s" + lineSeparator()
                        + " actual= %s", expected, actual),
                expected,
                actual);
    }

    @SuppressWarnings("unchecked")
    public static <E extends HasId,
            ID extends Serializable> BiConsumer<E[],
            Map<String, GenericAbstractCrudRepository<?, ID>>> getDeleteOneTestConsumer() {
        final BiConsumer<E[], Map<String, GenericAbstractCrudRepository<?, ID>>> consumer;
        consumer = (ar, repositoryMap) -> {
            E expected = ar[1];
            E toDelete = ar[0];

            GenericAbstractCrudRepository<User, Integer> userRepository
                    = (GenericAbstractCrudRepository<User, Integer>) repositoryMap.get
                    ("DataJpaUserRepository");

            GenericAbstractCrudRepository<TaskContext, Integer> taskContextRepository
                    = (GenericAbstractCrudRepository<TaskContext, Integer>) repositoryMap.get
                    ("DataJpaTaskContextRepository");

            GenericAbstractCrudRepository<TaskPortfolio, Integer> taskPortfolioRepository
                    = (GenericAbstractCrudRepository<TaskPortfolio, Integer>) repositoryMap.get
                    ("DataJpaTaskPortfolioRepository");

            GenericAbstractCrudRepository<Task, Integer> taskRepository
                    = (GenericAbstractCrudRepository<Task, Integer>) repositoryMap.get
                    ("DataJpaTaskRepository");


            LOGGER.info(String.format("Expected=%s", expected), expected);
            LOGGER.info(String.format("toDelete=%s", toDelete), toDelete);

            if (toDelete instanceof TaskContext) {

                //get current instance
                TaskContext currentToDelete = taskContextRepository.findById(toDelete.getId()).orElseThrow(() -> new
                        NotFoundException("TaskContext to delete not found"));
                getDeleteOneTaskContextTestConsumerSupport(taskContextRepository, userRepository, taskRepository,
                        currentToDelete, (TaskContext) expected);

            } else if (toDelete instanceof TaskPortfolio) {

                //get current instance
                TaskPortfolio currentToDelete = taskPortfolioRepository.findById(toDelete.getId()).orElseThrow(() -> new
                        NotFoundException("TaskPortfolio to delete not found"));
                getDeleteOneTaskPortfolioTestConsumerSupport(taskPortfolioRepository, userRepository, taskRepository,
                        currentToDelete, (TaskPortfolio) expected);

            } else if (toDelete instanceof Task) {

                //get current instance
                Task currentToDelete = taskRepository.findById(toDelete.getId()).orElseThrow(() -> new
                        NotFoundException("Task to delete not found"));
                getDeleteOneTaskTestConsumerSupport(taskRepository, userRepository, taskContextRepository,
                        taskPortfolioRepository, currentToDelete, (Task) expected);

            } else if (toDelete instanceof User) {

                User currentToDelete = userRepository.findById(toDelete.getId()).orElseThrow(() -> new
                        NotFoundException("User to delete not found"));
                getDeleteOneUserTestConsumerSupport(userRepository, currentToDelete, (User) expected);
            }
        };
        return consumer;
    }
}