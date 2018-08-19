package ua.epam.spring.hometask;

import ua.epam.spring.hometask.domain.Role;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.matcher.ModelMatcher;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by Vladimir on 10.10.2017.
 */
public class UserTestData {

    public static final ModelMatcher<User> MATCHER =
            new ModelMatcher<>(
                    Comparator.comparing(User::getEmail)
                            .thenComparing(User::getFirstName)
                            .thenComparing(User::getLastName));

    public static final User USER1 = new User();
    public static final User USER2 = new User();
    public static final User USER3 = new User();

    public static final String EMAIL1 = "user1@epam.com";
    public static final String EMAIL2 = "user2@epam.com";
    public static final String EMAIL3 = "user3@epam.com";

    public static final String USER_NAME1 = "User1";
    public static final String USER_NAME2 = "User2";
    public static final String USER_NAME3 = "User3";

    public static final String LAST_NAME1 = "LastUser1";
    public static final String LAST_NAME2 = "LastUser2";
    public static final String LAST_NAME3 = "LastUser3";

    public static final LocalDate USER_BIRTHDAY1 = LocalDate.of(1980,1,5);
    public static final LocalDate USER_BIRTHDAY2 = LocalDate.of(1980,1,7);

    public static final String PASSWORD = "Password";
    public static final Set<Role> ROLE_SET = EnumSet.of(Role.REGISTERED_USER);

    static{
        USER1.setEmail(EMAIL1);
        USER1.setFirstName(USER_NAME1);
        USER1.setLastName(LAST_NAME1);

        USER2.setEmail(EMAIL2);
        USER2.setFirstName(USER_NAME2);
        USER2.setLastName(LAST_NAME2);

        USER3.setEmail(EMAIL3);
        USER3.setFirstName(USER_NAME3);
        USER3.setLastName(LAST_NAME3);
    }



    public static User createNew(String email, String name, String lastName, LocalDate birthday, String password, Set<Role> roles){
        User u = new User();
        u.setEmail(email);
        u.setFirstName(name);
        u.setLastName(lastName);
        u.setBirthday(birthday);
        u.setPassword(password);
        u.setRoles(roles);
        return u;
    }
}
