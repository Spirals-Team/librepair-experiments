package data;

import com.meteoradesigner.model.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.meteoradesigner.model.Role.ROLE_ADMIN;
import static com.meteoradesigner.model.Role.ROLE_USER;
import static java.util.Arrays.asList;

/**
 * This class @code{DataJpaUserRepositoryTestData} holds of saveOneTest data
 * for @code{DataJpaUserRepository}.
 */
//TODO documentation.
public class DataJpaUserRepositoryTestData extends GenericDataJpaRepositoryTestData<User> {
    protected static final User CONSTRUCTED_BY_H2SQL_SCRIPT_USER_1;
    protected static final User CONSTRUCTED_BY_H2SQL_SCRIPT_USER_2;
    protected static final User CONSTRUCTED_BY_H2SQL_SCRIPT_USER_3;
    protected static final User CONSTRUCTED_BY_H2SQL_SCRIPT_USER_4;

    private static final User USER_TO_SAVE_ONE_FIRST;
    private static final User ADMIN_TO_SAVE_ONE_FIRST;
    private static final User USER_ADMIN_TO_SAVE_ONE_SECOND;
    private static final Collection<User[]> USER_REPOSITORY_SAVE_ONE_PARAMETRIZED;

    private static final User USER_TO_FIND_ONE_FIRST_EXPECTED;
    private static final User USER_ADMIN_TO_FIND_ONE_SECOND_EXPECTED;
    private static final Collection<User[]> USER_REPOSITORY_FIND_ONE_PARAMETRIZED;

    private static final Collection<User[]> USER_REPOSITORY_DELETE_ONE_PARAMETRIZED;
    public static final List<User> USER_REPOSITORY_FIND_ALL_COMMON;

    public static final String USER_REPOSITORY_MAIL_TO_FIND_BY_MAIL;
    public static final User USER_REPOSITORY_CUSTOM;
    public static final String USER_REPOSITORY_DISPLAY_NAME_TO_FIND_BY_DISPLAY_NAME;

    //TODO add more saveOneTest logic, for example initialize all the fields
    static {

        //constructs data for common tests
        CONSTRUCTED_BY_H2SQL_SCRIPT_USER_1 = new User(1,
                                                      "initializedBySqlScriptUser1DisplayName",
                                                      "initializedBySqlScriptUser1@email.com",
                                                      "initializedBySqlScriptUser1Password",
                                                      ROLE_USER);
        CONSTRUCTED_BY_H2SQL_SCRIPT_USER_2 = new User(2,
                                                      "initializedBySqlScriptUser2DisplayName",
                                                      "initializedBySqlScriptUser2@email.com",
                                                      "initializedBySqlScriptUser2Password",
                                                      ROLE_USER);
        CONSTRUCTED_BY_H2SQL_SCRIPT_USER_3 = new User(3,
                                                      "initializedBySqlScriptAdmin3DisplayName",
                                                      "initializedBySqlScriptAdmin3@email.com",
                                                      "initializedBySqlScriptAdmin3Password",
                                                      ROLE_ADMIN);
        CONSTRUCTED_BY_H2SQL_SCRIPT_USER_4 = new User(4,
                                                      "initializedBySqlScriptUserAdmin4DisplayName",
                                                      "initializedBySqlScriptUserAdmin4@email.com",
                                                      "initializedBySqlScriptUserAdmin4Password",
                                                      ROLE_USER, ROLE_ADMIN);

        USER_REPOSITORY_FIND_ALL_COMMON = asList(
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_1,
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_2,
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_3,
                CONSTRUCTED_BY_H2SQL_SCRIPT_USER_4
        );

        //TODO add tests/testData to check updating
        //constructs data for save/update tests
        USER_TO_SAVE_ONE_FIRST = new User(5,
                                          "UserToSaveOneFirst",
                                          "UserToSaveOneFirst@email.com",
                                          "UserToSaveOneFirstPassword",
                                          ROLE_USER);

        ADMIN_TO_SAVE_ONE_FIRST = new User(5,
                                           "AdminToSaveOneFirst",
                                           "AdminToSaveOneFirst@email.com",
                                           "AdminToSaveOneFirstPassword",
                                           ROLE_ADMIN);

        USER_ADMIN_TO_SAVE_ONE_SECOND = new User(5,
                                                 "UserAdminToSaveOneSecond",
                                                 "UserAdminToSaveOneSecond@email.com",
                                                 "UserAdminToSaveOneSecondPassword",
                                                 ROLE_ADMIN, ROLE_USER);

        USER_REPOSITORY_SAVE_ONE_PARAMETRIZED = asList(new User[][]{
                {USER_TO_SAVE_ONE_FIRST, USER_TO_SAVE_ONE_FIRST},
                {ADMIN_TO_SAVE_ONE_FIRST, ADMIN_TO_SAVE_ONE_FIRST},
                {USER_ADMIN_TO_SAVE_ONE_SECOND, USER_ADMIN_TO_SAVE_ONE_SECOND},
                });

        //constructs data for find tests
        USER_TO_FIND_ONE_FIRST_EXPECTED = CONSTRUCTED_BY_H2SQL_SCRIPT_USER_1;
        USER_ADMIN_TO_FIND_ONE_SECOND_EXPECTED = CONSTRUCTED_BY_H2SQL_SCRIPT_USER_4;

        USER_REPOSITORY_FIND_ONE_PARAMETRIZED = asList(new User[][]{
                {USER_TO_FIND_ONE_FIRST_EXPECTED, USER_TO_FIND_ONE_FIRST_EXPECTED},
                {USER_ADMIN_TO_FIND_ONE_SECOND_EXPECTED, USER_ADMIN_TO_FIND_ONE_SECOND_EXPECTED},
                });

        //constructs data for delete tests
        USER_REPOSITORY_DELETE_ONE_PARAMETRIZED = Stream.of(USER_REPOSITORY_FIND_ALL_COMMON)
                                                        .flatMap(Collection::stream)
                                                        .map(u -> new User[]{u, null})
                                                        .collect(Collectors.toList());

        //constructs data for custom tests
        USER_REPOSITORY_MAIL_TO_FIND_BY_MAIL = "initializedBySqlScriptUserAdmin4@email.com";
        USER_REPOSITORY_DISPLAY_NAME_TO_FIND_BY_DISPLAY_NAME
                = "initializedBySqlScriptUserAdmin4DisplayName";
        USER_REPOSITORY_CUSTOM = CONSTRUCTED_BY_H2SQL_SCRIPT_USER_4;
    }

    public Collection<User[]> getSaveOneTestData() {
        return USER_REPOSITORY_SAVE_ONE_PARAMETRIZED;
    }

    public Collection<User[]> getFindOneTestData() {
        return USER_REPOSITORY_FIND_ONE_PARAMETRIZED;
    }

    public Collection<User[]> getDeleteOneTestData() {
        return USER_REPOSITORY_DELETE_ONE_PARAMETRIZED;
    }

    public List<User> getFindAllTestData() {
        return USER_REPOSITORY_FIND_ALL_COMMON;
    }
}