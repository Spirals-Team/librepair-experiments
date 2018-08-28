package ru.job4j.bank;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тесты банка.
 */
public class TestBankingSystem {
    /**
     * Тест добавления пользователя.
     */
    @Test
    public void addUser() {
        User user = new User("Владимир", 112233);
        Actions actions = new Actions();
        actions.addUser(user);
        assertThat(actions.getClients().isEmpty(), is(false));
    }

    /**
     * Тест удаления пользователя.
     */
    @Test
    public void deleteUser() {
        User user = new User("Владимир", 112233);
        Actions actions = new Actions();
        actions.deleteUser(user);
        assertThat(actions.getClients().isEmpty(), is(true));
    }

    /**
     * Тест добавления счета пользователю.
     */
    @Test
    public void addAccountToUser() {
        User user = new User("Владимир", 112233);
        Account accountUser = new Account(1000, 00005555);
        Actions actions = new Actions();
        actions.addUser(user);
        actions.addAccountToUser(user, accountUser);
        assertThat(actions.getClients().get(user).isEmpty(), is(false));
    }

    /**
     * Тест удаления счета у пользователя.
     */
    @Test
    public void deleteAccountFromUser() {
        User user = new User("Владимир", 112233);
        Account accountUser = new Account(1000, 00005555);
        Actions actions = new Actions();
        actions.addUser(user);
        actions.addAccountToUser(user, accountUser);
        actions.deleteAccountFromUser(user, accountUser);
        assertThat(actions.getClients().get(user).isEmpty(), is(true));
    }

    /**
     * Тест получения счетов пользователя.
     */
    @Test
    public void getUserAccounts() {
        User user = new User("Владимир", 112233);
        Account accountUser = new Account(1000, 00005555);
        Account accountUser2 = new Account(2000, 55550000);
        Actions actions = new Actions();
        actions.addUser(user);
        actions.addAccountToUser(user, accountUser);
        actions.addAccountToUser(user, accountUser2);
        assertThat(actions.getUserAccounts(user).get(0).getRequisites(), is(accountUser.getRequisites()));
        assertThat(actions.getUserAccounts(user).get(1).getRequisites(), is(accountUser2.getRequisites()));
    }

    /**
     * Тест перевода денег одного пользователя другому.
     */
    @Test
    public void transferMoney() {
        User user = new User("Владимир", 111333);
        User user2 = new User("Владимир", 222333);
        Account accountUser = new Account(1000, 00005555);
        Account accountUser2 = new Account(1000, 55550000);
        Actions actions = new Actions();
        actions.addUser(user);
        actions.addUser(user2);
        actions.addAccountToUser(user, accountUser);
        actions.addAccountToUser(user2, accountUser2);
        boolean resault = actions.transferMoney(user, accountUser, user2, accountUser2, 500);
        assertThat(true, is((accountUser.getValue() == 500) && (accountUser2.getValue() == 1500) && resault));
    }
}
