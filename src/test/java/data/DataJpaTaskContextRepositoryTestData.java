package data;

import com.meteoradesigner.model.TaskContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static data.DataJpaUserRepositoryTestData.CONSTRUCTED_BY_H2SQL_SCRIPT_USER_1;
import static data.DataJpaUserRepositoryTestData.CONSTRUCTED_BY_H2SQL_SCRIPT_USER_2;
import static data.DataJpaUserRepositoryTestData.CONSTRUCTED_BY_H2SQL_SCRIPT_USER_4;
import static java.util.Arrays.asList;

/**
 * This class @code{DataJpaTaskContextRepositoryTestData} holds of saveOneTest data
 * for @code{DataJpaTaskContextRepository}.
 */
//TODO documentation.
public class DataJpaTaskContextRepositoryTestData extends
        GenericDataJpaRepositoryTestData<TaskContext> {
    protected static final TaskContext CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_1;
    protected static final TaskContext CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_2;
    protected static final TaskContext CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_3;
    protected static final TaskContext CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_4;
    protected static final TaskContext CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_5;
    protected static final TaskContext CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_6;
    protected static final TaskContext CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_7;
    protected static final TaskContext CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_8;
    private static final List<TaskContext> TASK_CONTEXT_REPOSITORY_FIND_ALL_COMMON;
    private static final TaskContext TASK_CONTEXT_TO_SAVE_ONE_FIRST;
    private static final TaskContext TASK_CONTEXT_TO_SAVE_ONE_SECOND;
    private static final Collection<TaskContext[]>
            TASK_CONTEXT_REPOSITORY_SAVE_ONE_PARAMETRIZED;
    private static final TaskContext TASK_CONTEXT_TO_FIND_ONE_FIRST_EXPECTED;
    private static final TaskContext TASK_CONTEXT_TO_FIND_ONE_SECOND_EXPECTED;
    private static final Collection<TaskContext[]>
            TASK_CONTEXT_REPOSITORY_FIND_ONE_PARAMETRIZED;

    private static final Collection<TaskContext[]>
            TASK_CONTEXT_REPOSITORY_DELETE_ONE_PARAMETRIZED;

    private static final Logger LOGGER = LoggerFactory.getLogger(
            DataJpaTaskContextRepositoryTestData.class);

    //TODO add more saveOneTest logic, for example initialize all the fields
    static {

        //constructs data for common tests
        CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_1 = new TaskContext(1,
                "initializedBySqlScriptUser1Context1DisplayName",
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_1,
                "initializedBySqlScriptUser1Context1Description");
        CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_2 = new TaskContext(2,
                "initializedBySqlScriptUser1Context2DisplayName",
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_1,
                "initializedBySqlScriptUser1Context2Description");
        CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_3 = new TaskContext(3,
                "initializedBySqlScriptUser2Context3DisplayName",
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_2,
                "initializedBySqlScriptUser2Context3Description");
        CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_4 = new TaskContext(4,
                "initializedBySqlScriptUser2Context4WithExternalContext5And6WithInternalContext7And8DisplayName",
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_2,
                "initializedBySqlScriptUser2Context4Description");
        CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_5 = new TaskContext(5,
                "initializedBySqlScriptUser2Context5WithInternalContext4DisplayName",
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_2,
                "initializedBySqlScriptUser2Context5Description");
        CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_6 = new TaskContext(6,
                "initializedBySqlScriptUser2Context6WithInternalContext4DisplayName",
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_2,
                "initializedBySqlScriptUser2Context6Description");
        CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_7 = new TaskContext(7,
                "initializedBySqlScriptUser2Context7WithExternalContext4DisplayName",
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_2,
                "initializedBySqlScriptUser2Context7Description");
        CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_8 = new TaskContext(8,
                "initializedBySqlScriptUser2Context8WithExternalContext4DisplayName",
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_2,
                "initializedBySqlScriptUser2Context8Description");

        TASK_CONTEXT_REPOSITORY_FIND_ALL_COMMON = asList(
                CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_1,
                CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_2,
                CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_3,
                CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_4,
                CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_5,
                CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_6,
                CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_7,
                CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_8
        );

        //TODO add tests/testData to check updating
        //constructs data for save/update tests

        TASK_CONTEXT_TO_SAVE_ONE_FIRST = new TaskContext(
                CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_8.getId() + 1,
                "TaskContextToSaveOneFirst",
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_1,
                "TaskContextToSaveOneFirstDescription");

        TASK_CONTEXT_TO_SAVE_ONE_SECOND = new TaskContext(
                CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_8.getId() + 1,
                "TaskContextToSaveOneSecond",
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_4,
                "TaskContextToSaveOneSecondDescription");

        TASK_CONTEXT_REPOSITORY_SAVE_ONE_PARAMETRIZED = asList(new TaskContext[][]{
                {TASK_CONTEXT_TO_SAVE_ONE_FIRST, TASK_CONTEXT_TO_SAVE_ONE_FIRST},
                {TASK_CONTEXT_TO_SAVE_ONE_SECOND, TASK_CONTEXT_TO_SAVE_ONE_SECOND},
        });

        //constructs data for find tests
        TASK_CONTEXT_TO_FIND_ONE_FIRST_EXPECTED = CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_1;
        TASK_CONTEXT_TO_FIND_ONE_SECOND_EXPECTED = CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_CONTEXT_8;

        TASK_CONTEXT_REPOSITORY_FIND_ONE_PARAMETRIZED = asList(new TaskContext[][]{
                {TASK_CONTEXT_TO_FIND_ONE_FIRST_EXPECTED, TASK_CONTEXT_TO_FIND_ONE_FIRST_EXPECTED},
                {TASK_CONTEXT_TO_FIND_ONE_SECOND_EXPECTED,
                        TASK_CONTEXT_TO_FIND_ONE_SECOND_EXPECTED},
        });

        //constructs data for delete tests
        TASK_CONTEXT_REPOSITORY_DELETE_ONE_PARAMETRIZED = Stream.of
                (TASK_CONTEXT_REPOSITORY_FIND_ALL_COMMON)
                .flatMap(Collection::stream).map(c -> new TaskContext[]{c, null}).collect(Collectors.toList());

        //constructs data for custom tests
        //TODO construct data for custom tests
    }

    public Collection<TaskContext[]> getSaveOneTestData() {
        return TASK_CONTEXT_REPOSITORY_SAVE_ONE_PARAMETRIZED;
    }

    public Collection<TaskContext[]> getFindOneTestData() {
        return TASK_CONTEXT_REPOSITORY_FIND_ONE_PARAMETRIZED;
    }

    public Collection<TaskContext[]> getDeleteOneTestData() {
        return TASK_CONTEXT_REPOSITORY_DELETE_ONE_PARAMETRIZED;
    }

    public List<TaskContext> getFindAllTestData() {
        return TASK_CONTEXT_REPOSITORY_FIND_ALL_COMMON;
    }
}