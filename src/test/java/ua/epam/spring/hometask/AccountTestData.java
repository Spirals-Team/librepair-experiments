package ua.epam.spring.hometask;

import ua.epam.spring.hometask.domain.UserAccount;
import ua.epam.spring.hometask.matcher.ModelMatcher;

import java.util.Comparator;

public class AccountTestData {

    public static final ModelMatcher<UserAccount> MATCHER =
            new ModelMatcher<>(
                    Comparator.comparing(UserAccount::getName)
                            .thenComparing(UserAccount::getMoney));

    public static final String ACCOUNT_NAME_1_1 = "ACC_1_1";
    public static final String ACCOUNT_NAME_1_2 = "ACC_1_2";
    public static final String ACCOUNT_NAME_1_3 = "ACC_1_3";

    public static final double MONEY_1_1 = 10;
    public static final double MONEY_1_2 = 10.5;
    public static final double MONEY_1_3 = 10.55;

    public static final UserAccount USER_ACCOUNT1 = new UserAccount();
    public static final UserAccount USER_ACCOUNT2 = new UserAccount();
    public static final UserAccount USER_ACCOUNT3 = new UserAccount();

    static{
        USER_ACCOUNT1.setName(ACCOUNT_NAME_1_1);
        USER_ACCOUNT1.setMoney(MONEY_1_1);

        USER_ACCOUNT2.setName(ACCOUNT_NAME_1_2);
        USER_ACCOUNT2.setMoney(MONEY_1_2);

        USER_ACCOUNT3.setName(ACCOUNT_NAME_1_3);
        USER_ACCOUNT3.setMoney(MONEY_1_3);
    }

}
