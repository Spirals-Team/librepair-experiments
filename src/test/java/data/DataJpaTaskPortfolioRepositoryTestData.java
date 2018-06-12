package data;

import com.meteoradesigner.model.TaskPortfolio;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static data.DataJpaTaskRepositoryTestData.CONSTRUCTED_PROGRAMMATICALLY_TASK_1;
import static data.DataJpaTaskRepositoryTestData.CONSTRUCTED_PROGRAMMATICALLY_TASK_15;
import static data.DataJpaTaskRepositoryTestData.CONSTRUCTED_PROGRAMMATICALLY_TASK_2;
import static data.DataJpaTaskRepositoryTestData.CONSTRUCTED_PROGRAMMATICALLY_TASK_3;
import static data.DataJpaUserRepositoryTestData.CONSTRUCTED_BY_H2SQL_SCRIPT_USER_1;
import static data.DataJpaUserRepositoryTestData.CONSTRUCTED_BY_H2SQL_SCRIPT_USER_2;
import static data.DataJpaUserRepositoryTestData.CONSTRUCTED_BY_H2SQL_SCRIPT_USER_4;
import static java.util.Arrays.asList;

//TODO documentation
public class DataJpaTaskPortfolioRepositoryTestData
        extends GenericDataJpaRepositoryTestData<TaskPortfolio> {

    protected static final TaskPortfolio CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_PORTFOLIO_1;
    protected static final TaskPortfolio CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_PORTFOLIO_2;
    protected static final TaskPortfolio CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_PORTFOLIO_3;
    protected static final TaskPortfolio CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_PORTFOLIO_4;
    protected static final List<TaskPortfolio> PORTFOLIO_REPOSITORY_FIND_ALL_COMMON;

    private static final TaskPortfolio TASK_PORTFOLIO_TO_SAVE_ONE_FIRST;
    private static final TaskPortfolio TASK_PORTFOLIO_TO_SAVE_ONE_SECOND;
    protected static final List<TaskPortfolio[]> TASK_PORTFOLIO_REPOSITORY_SAVE_ONE_PARAMETRIZED;

    private static final TaskPortfolio TASK_PORTFOLIO_TO_FIND_ONE_FIRST_EXPECTED;
    private static final TaskPortfolio TASK_PORTFOLIO_TO_FIND_ONE_SECOND_EXPECTED;
    protected static final List<TaskPortfolio[]> TASK_PORTFOLIO_REPOSITORY_FIND_ONE_PARAMETRIZED;

    protected static final List<TaskPortfolio[]> TASK_PORTFOLIO_REPOSITORY_DELETE_ONE_PARAMETRIZED;

    static {

        //constructs data for common tests
        CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_PORTFOLIO_1 = new TaskPortfolio(
                1,
                "initializedBySqlScriptUser1Portfolio1DisplayName",
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_1,
                "initializedBySqlScriptUser1Portfolio1Description",
                new HashSet<>(asList(
                        CONSTRUCTED_PROGRAMMATICALLY_TASK_1, CONSTRUCTED_PROGRAMMATICALLY_TASK_2)));
        CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_PORTFOLIO_2 = new TaskPortfolio(
                2,
                "initializedBySqlScriptUser1Portfolio2DisplayName",
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_1,
                "initializedBySqlScriptUser1Portfolio2Description",
                null);

        CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_PORTFOLIO_3 = new TaskPortfolio(
                3,
                "initializedBySqlScriptUser2Portfolio3DisplayName",
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_2,
                "initializedBySqlScriptUser2Portfolio3Description",
                new HashSet<>(asList(CONSTRUCTED_PROGRAMMATICALLY_TASK_3)));

        CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_PORTFOLIO_4 = new TaskPortfolio(
                4,
                "initializedBySqlScriptUser2Portfolio4DisplayName",
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_2,
                "initializedBySqlScriptUser2Portfolio4Description",
                null);

        PORTFOLIO_REPOSITORY_FIND_ALL_COMMON = asList(
                CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_PORTFOLIO_1,
                CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_PORTFOLIO_2,
                CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_PORTFOLIO_3,
                CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_PORTFOLIO_4
        );

        //TODO add tests/testData to check updating
        //constructs data for save/update tests
        TASK_PORTFOLIO_TO_SAVE_ONE_FIRST = new TaskPortfolio(
                5,
                "TaskPortfolioToSaveOneFirstName",
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_4,
                "TaskPortfolioToSaveOneFirstDescription",
                new HashSet<>(asList(
                        CONSTRUCTED_PROGRAMMATICALLY_TASK_1,
                        CONSTRUCTED_PROGRAMMATICALLY_TASK_15
                ))
        );
        TASK_PORTFOLIO_TO_SAVE_ONE_SECOND = new TaskPortfolio(
                5,
                "TaskPortfolioToSaveOneSecondName",
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_1,
                "TaskPortfolioToSaveOneSecondDescription",
                Collections.emptySet()
        );

        TASK_PORTFOLIO_REPOSITORY_SAVE_ONE_PARAMETRIZED = asList(new TaskPortfolio[][]{
                {TASK_PORTFOLIO_TO_SAVE_ONE_FIRST, TASK_PORTFOLIO_TO_SAVE_ONE_FIRST},
                {TASK_PORTFOLIO_TO_SAVE_ONE_SECOND, TASK_PORTFOLIO_TO_SAVE_ONE_SECOND},
                });

        //constructs data for find tests
        TASK_PORTFOLIO_TO_FIND_ONE_FIRST_EXPECTED = CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_PORTFOLIO_1;
        TASK_PORTFOLIO_TO_FIND_ONE_SECOND_EXPECTED = CONSTRUCTED_BY_H2SQL_SCRIPT_TASK_PORTFOLIO_2;

        TASK_PORTFOLIO_REPOSITORY_FIND_ONE_PARAMETRIZED = asList(new TaskPortfolio[][]{
                {TASK_PORTFOLIO_TO_FIND_ONE_FIRST_EXPECTED,
                 TASK_PORTFOLIO_TO_FIND_ONE_FIRST_EXPECTED},
                {TASK_PORTFOLIO_TO_FIND_ONE_SECOND_EXPECTED,
                 TASK_PORTFOLIO_TO_FIND_ONE_SECOND_EXPECTED},
                });

        //constructs data for delete tests
        TASK_PORTFOLIO_REPOSITORY_DELETE_ONE_PARAMETRIZED = Stream.of
                (PORTFOLIO_REPOSITORY_FIND_ALL_COMMON)
                                                                  .flatMap(Collection::stream)
                                                                  .map(p -> new TaskPortfolio[]{p,
                                                                                                null})
                                                                  .collect(Collectors.toList());

        //constructs data for custom tests
        //TODO construct data for custom tests
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<TaskPortfolio[]> getSaveOneTestData() {
        return TASK_PORTFOLIO_REPOSITORY_SAVE_ONE_PARAMETRIZED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<TaskPortfolio[]> getFindOneTestData() {
        return TASK_PORTFOLIO_REPOSITORY_FIND_ONE_PARAMETRIZED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<TaskPortfolio[]> getDeleteOneTestData() {
        return TASK_PORTFOLIO_REPOSITORY_DELETE_ONE_PARAMETRIZED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TaskPortfolio> getFindAllTestData() {
        return PORTFOLIO_REPOSITORY_FIND_ALL_COMMON;
    }
}