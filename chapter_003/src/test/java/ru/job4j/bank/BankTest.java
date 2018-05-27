package ru.job4j.bank;

import org.junit.Test;

import java.util.ArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class BankTest {
    @Test
    public void whenAddUser() {
        Bank bank = new Bank();
        User user = new User("Ivan", "78 00");
        bank.addUser(user);
        assertTrue(bank.users.containsKey(user));
    }

    @Test
    public void whenDeleteUser() {
        Bank bank = new Bank();
        User user = new User("Ivan", "78 00");
        bank.addUser(user);
        bank.deleteUser(user);
        assertTrue(bank.users.isEmpty());
    }

    @Test
    public void whenAddAccountToUser() {
        Bank bank = new Bank();
        User user = new User("Ivan", "78 00");
        bank.addUser(user);
        bank.addAccountToUser("78 00", new Account(10, "100 500"));
        ArrayList<Account> expect = new ArrayList<>();
        expect.add(new Account(10, "100 500"));
        assertThat(bank.users.get(user), is(expect));
    }

    @Test
    public void whenDeleteAccountFromUser() {
        Bank bank = new Bank();
        User user = new User("Ivan", "78 00");
        bank.addUser(user);
        bank.addAccountToUser("78 00", new Account(1000000, "100 500"));
        bank.addAccountToUser("78 00", new Account(10, "10 2000 78"));
        ArrayList<Account> expect = new ArrayList<>();
        expect.add(new Account(10, "10 2000 78"));
        bank.deleteAccountFromUser("78 00", new Account(0, "100 500"));
        assertThat(bank.users.get(user), is(expect));
    }

    @Test
    public void whenGetUserAccounts() {
        Bank bank = new Bank();
        User user = new User("Ivan", "78 00");
        bank.addUser(user);
        bank.addAccountToUser("78 00", new Account(200, "100 500"));
        bank.addAccountToUser("78 00", new Account(10, "10 2000 78"));
        ArrayList<Account> expect = new ArrayList<>();
        expect.add(new Account(200, "100 500"));
        expect.add(new Account(10, "10 2000 78"));
        assertThat(bank.getUserAccounts("78 00"), is(expect));
    }

    @Test
    public void whenTransferMoney() {
        Bank bank = new Bank();
        User userOne = new User("Ivan", "78 00");
        User userTwo = new User("Alex", "45 00");
        bank.addUser(userOne);
        bank.addUser(userTwo);
        bank.addAccountToUser(userOne.getPasport(), new Account(10, "10 2000 78"));
        bank.addAccountToUser(userTwo.getPasport(), new Account(2000, "10 4000 08"));
        bank.transferMoney(
                userTwo.getPasport(), "10 4000 08", userOne.getPasport(), "10 2000 78",
                455.25
        );
        ArrayList<Account> test = new ArrayList<>();
        test.add(bank.users.get(userOne).get(0));
        test.add(bank.users.get(userTwo).get(0));
        ArrayList<Account> expect = new ArrayList<>();
        expect.add(new Account(465.25, "10 2000 78"));
        expect.add(new Account(1544.75, "10 4000 08"));
        assertThat(test, is(expect));
    }
}