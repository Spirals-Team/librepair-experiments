package ru.job4j.collections.tasktest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class OperationTest.
 */
public class OperationTest {
    /**
     * Test Add user.
     */
    @Test
    public void whenAddUserThenMapSizeIncrease() {
        Operation operation = new Operation();
        User user1 = new User("Igor", "01 00 000000");
        operation.addUser(user1);
        int result = operation.getInfo().size();
        int exist = 1;
        assertThat(result, is(exist));
    }

    /**
     * Test Delete user.
     */
    @Test
    public void whenDeleteUserThenMapSizeDecrease() {
        Operation operation = new Operation();
        User user1 = new User("Igor", "01 00 000000");
        operation.addUser(user1);
        operation.deleteUser(user1);
        int result = operation.getInfo().size();
        int exist = 0;
        assertThat(result, is(exist));
    }

    /**
     * Test add account for user.
     */
    @Test
    public void whenAddAccountThenUserListIncrease() {
        Operation operation = new Operation();
        User user1 = new User("Igor", "01 00 000000");
        operation.addUser(user1);
        Account account1 = new Account(35000, "0000 0000 0000 0001");
        operation.addAccountToUser(user1, account1);
        Account account2 = new Account(3000, "0000 0000 0000 0002");
        operation.addAccountToUser(user1, account2);
        int result = operation.getInfo().get(user1).size();
        int exist = 2;
        assertThat(result, is(exist));
    }

    /**
     * Test Delete account for user.
     */
    @Test
    public void whenDeleteAccountThenUserListDecrease() {
        Operation operation = new Operation();
        User user1 = new User("Igor", "01 00 000000");
        operation.addUser(user1);
        Account account1 = new Account(35000, "0000 0000 0000 0001");
        operation.addAccountToUser(user1, account1);
        Account account2 = new Account(3000, "0000 0000 0000 0002");
        operation.addAccountToUser(user1, account2);
        operation.deleteAccountFromUser(user1, account1);
        int result = operation.getInfo().get(user1).size();
        int exist = 1;
        assertThat(result, is(exist));
    }

    /**
     * Test Get user accounts.
     */
    @Test
    public void whenGetUserAccountsWhenReturnListOfAccounts() {
        Operation operation = new Operation();

        User user1 = new User("Igor", "01 00 000000");
        operation.addUser(user1);
        Account account1 = new Account(35000, "0000 0000 0000 0001");
        operation.addAccountToUser(user1, account1);
        Account account2 = new Account(3000, "0000 0000 0000 0002");
        operation.addAccountToUser(user1, account2);

        List<Account> exist = new ArrayList<>();
        exist.add(account1);
        exist.add(account2);

        List<Account> result = operation.getUserAccounts(user1);
        assertThat(result, is(exist));
    }

    /**
     * Test successful transfer money.
     */
    @Test
    public void whenAccount1DecreaseThenAccount2Increase() {
        Operation operation = new Operation();

        User user1 = new User("Igor", "01 00 000000");
        operation.addUser(user1);
        Account account1 = new Account(35000, "0000 0000 0000 0001");
        operation.addAccountToUser(user1, account1);

        User user2 = new User("Andrey", "02 00 000000");
        operation.addUser(user2);
        Account account2 = new Account(3000, "0000 0000 0000 0002");
        operation.addAccountToUser(user2, account2);

        operation.transferMoney(user1, account1, user2, account2, 5000);

        double result = account2.getValue();
        double exist = 8000;
        assertThat(result, is(exist));
    }

    /**
     * Test unsuccessful transfer money.
     */
    @Test
    public void whenUser1DontHaveEnoughMoneyThenUser2DoNotGetMoney() {
        Operation operation = new Operation();

        User user1 = new User("Igor", "01 00 000000");
        operation.addUser(user1);
        Account account1 = new Account(5000, "0000 0000 0000 0001");
        operation.addAccountToUser(user1, account1);

        User user2 = new User("Andrey", "02 00 000000");
        operation.addUser(user2);
        Account account2 = new Account(3000, "0000 0000 0000 0002");
        operation.addAccountToUser(user2, account2);

        boolean result = operation.transferMoney(user1, account1, user2, account2, 5000.01);
        boolean exist = false;
        assertThat(result, is(exist));
    }
}
