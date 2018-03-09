package ru.aserdyuchenko.testTask;

import org.junit.Test;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by anton on 05.05.17.
 */
public class BankTest {
    /**
     * Gringotts Wizarding Bank - банк в волшебном мире Гарри Поттера.
     * @param gringotts - bank.
     */
    Bank gringotts = new Bank();
    User userPotter = new User("Harry Potter", 1234);
    User userVoldemort = new User("Lord Voldemort", 4321);
    Account firstAccount = new Account(500, 1);
    Account secondAccount = new Account(100, 2);

    @Test
    public void whenAddNewUser() {
        gringotts.addUser(userPotter);
        List<Account> result = gringotts.getUserAccounts(userPotter);
        assertThat(result.toString(), is("[]"));
    }

    @Test (expected = java.lang.NullPointerException.class)
    public void whenDeleteUser() {
        gringotts.addUser(userPotter);
        gringotts.deleteUser(userPotter);
        gringotts.getUserAccounts(userPotter).toString();
    }

    @Test
    public  void whenAddAccount() {
        gringotts.addUser(userPotter);
        gringotts.addAccountToUser(userPotter, firstAccount);
        List<Account> result = gringotts.getUserAccounts(userPotter);
        assertThat(result.toString(), is("[Account{value=500.0, requisites=1}]"));
    }

    @Test
    public void whenDeleteAccount() throws Bank.UserNotFoundException {
        gringotts.addUser(userPotter);
        gringotts.addAccountToUser(userPotter, firstAccount);
        gringotts.deleteAccountFromUser(userPotter, firstAccount);
        List<Account> result = gringotts.getUserAccounts(userPotter);
        assertThat(result.toString(), is("[]"));
    }

    @Test (expected = Bank.UserNotFoundException.class)
    public void whenDeleteAccountAndUserDontExist() throws Bank.UserNotFoundException {
        gringotts.deleteAccountFromUser(userPotter, firstAccount);
        List<Account> result = gringotts.getUserAccounts(userPotter);
    }

    @Test
    public void whenPotterTransferMoneyToVoldemort() throws Bank.UserNotFoundException {
        gringotts.addUser(userPotter);
        gringotts.addUser(userVoldemort);
        gringotts.addAccountToUser(userPotter, firstAccount);
        gringotts.addAccountToUser(userVoldemort, secondAccount);
        gringotts.transferMoney(userPotter, firstAccount, userVoldemort, secondAccount, 500);
        List<Account> voldemortAccount = gringotts.getUserAccounts(userVoldemort);
        assertThat(voldemortAccount.toString(), is("[Account{value=600.0, requisites=2}]"));
        List<Account> potterAccount = gringotts.getUserAccounts(userPotter);
        assertThat(potterAccount.toString(), is("[Account{value=0.0, requisites=1}]"));
    }

    @Test
    public void whenPotterTransferMoneyToVoldemortWithNewAccount() throws Bank.UserNotFoundException {
        gringotts.addUser(userPotter);
        gringotts.addUser(userVoldemort);
        gringotts.addAccountToUser(userPotter, firstAccount);
        gringotts.transferMoney(userPotter, firstAccount, userVoldemort, new Account(0, 0), 500);
        List<Account> voldemortAccount = gringotts.getUserAccounts(userVoldemort);
        assertThat(voldemortAccount.toString(), is("[Account{value=500.0, requisites=0}]"));
        List<Account> potterAccount = gringotts.getUserAccounts(userPotter);
        assertThat(potterAccount.toString(), is("[Account{value=0.0, requisites=1}]"));
    }

    @Test
    public void whenPotterHasNotMoneyForVoldemart() throws Bank.UserNotFoundException {
        gringotts.addUser(userPotter);
        gringotts.addUser(userVoldemort);
        gringotts.addAccountToUser(userPotter, firstAccount);
        gringotts.addAccountToUser(userVoldemort, secondAccount);
        boolean isTransfer = gringotts.transferMoney(userPotter, firstAccount, userVoldemort, secondAccount, 999);
        assertThat(isTransfer, is(false));
    }
}