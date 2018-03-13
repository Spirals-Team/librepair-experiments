package ru.job4j.bank;

import org.junit.Test;

import ru.job4j.bank.*;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BankTest {

    @Test
    public void whenAddUserthenUserIsInMapUsersAccounts() {
        User newUser = new User("Serg", "1111");
        Bank bank = new Bank();
        bank.addUser(newUser);

        boolean resultUserIsInMap = bank.usersAccounts.containsKey(newUser);

        boolean expected = true;

        assertThat(resultUserIsInMap, is(expected));

    }

    @Test
    public void whendeleteUserThenThereIsNoUserInMapUsersAccounts() {
        User newUser = new User("Serg", "1111");
        Bank bank = new Bank();
        bank.addUser(newUser);
        bank.deleteUser(newUser);

        boolean resultUserIsInMap = bank.usersAccounts.containsKey(newUser);

        boolean expected = false;

        assertThat(resultUserIsInMap, is(expected));
    }

    @Test
    public void whenAddAccountToUserThenAccountIsInMapWithUsersKey() {
        User newUser = new User("Serg", "2558");

        Account newAccount = new Account(0, "407028101111");

        Bank bank = new Bank();
        bank.addUser(newUser);

        bank.addAccountToUser(newUser, newAccount);

        boolean newAccountIsInMap = bank.usersAccounts.get(newUser).contains(newAccount);

        boolean expected = true;

        assertThat(newAccountIsInMap, is(expected));
    }

    @Test
    public void whenDeleteAccountFromUserThenThereIsNoAccountInMap() {

        User newUser = new User("Serg", "2558");

        Account firstAccount = new Account(0, "407028101111");

        Account secondAccount = new Account(0, "407028102222");

        Bank bank = new Bank();

        bank.addUser(newUser);

        bank.addAccountToUser(newUser, firstAccount);
        bank.addAccountToUser(newUser, secondAccount);

        bank.deleteAccountFromUser(newUser, secondAccount);

        boolean firstAccountIsInMap = bank.usersAccounts.get(newUser).contains(firstAccount);
        boolean secondAccountIsNotInMap = !bank.usersAccounts.get(newUser).contains(secondAccount);

        boolean expected = true;

        assertThat(firstAccountIsInMap && secondAccountIsNotInMap, is(expected));
    }

    @Test
    public void whenGetAllUserAccountsThenGetnAllUserAccounts() {

        User newUser = new User("Serg", "2558");

        Account firstAccount = new Account(0, "407028101111");

        Account secondAccount = new Account(0, "407028102222");

        Bank bank = new Bank();

        bank.addUser(newUser);

        bank.addAccountToUser(newUser, firstAccount);
        bank.addAccountToUser(newUser, secondAccount);

        List<Account> resultAllUserAccounts = bank.getUserAccounts(newUser);

        List<Account> expectedUserAccounts = new ArrayList<>();
        expectedUserAccounts.addAll(Arrays.asList(
                new Account(0, "407028101111"),
                new Account(0, "407028102222")
        ));

        assertThat(resultAllUserAccounts, is(expectedUserAccounts));
    }

    @Test
    public void whenTransferMoneyFromOneUserToAnotherThenAmountIncreesOnOneAccountAndDecreesOnAnother() {

        User firstUser = new User("serg", "2558");
        User secondUser = new User("max", "3551");

        Account firsUserAccount = new Account(5000, "407028101111");
        Account secondUserAccount = new Account(0, "407028102222");

        Bank bank = new Bank();
        bank.addUser(firstUser);
        bank.addUser(secondUser);
        bank.addAccountToUser(firstUser, firsUserAccount);
        bank.addAccountToUser(secondUser, secondUserAccount);

        boolean result = bank.transferMoney(firstUser, firsUserAccount, secondUser, secondUserAccount, 2500);
        boolean expected = true;

        assertThat(result, is(expected));

    }

    @Test
    public void whenTransferMoneyFromUserToTheSameUserThenAmountEnreesesAtOneAccountAndDecreesesAtAnother() {

        User firstUser = new User("serg", "2558");


        Account firsAccount = new Account(5000, "407028101111");
        Account secondAccount = new Account(0, "407028102222");

        Bank bank = new Bank();
        bank.addUser(firstUser);
        bank.addAccountToUser(firstUser, firsAccount);
        bank.addAccountToUser(firstUser, secondAccount);

        boolean result = bank.transferMoney(firstUser, firsAccount, firstUser, secondAccount, 2500);
        boolean expected = true;

        assertThat(result, is(expected));

    }

    @Test
    public void whenTransferMoneyAndThereIsNotEnoughAmountWhenGetFalse() {

        User firstUser = new User("serg", "2558");


        Account firsAccount = new Account(2000, "407028101111");
        Account secondAccount = new Account(0, "407028102222");

        Bank bank = new Bank();
        bank.addUser(firstUser);
        bank.addAccountToUser(firstUser, firsAccount);
        bank.addAccountToUser(firstUser, secondAccount);

        boolean result = bank.transferMoney(firstUser, firsAccount, firstUser, secondAccount, 2500);
        boolean expected = false;

        assertThat(result, is(expected));

    }

    @Test
    public void whenTransferMoneyAndAccountIsNotFoundThenGetFalse() {

        User firstUser = new User("serg", "2558");


        Account firsAccount = new Account(5000, "407028101111");
        Account secondAccount = new Account(0, "407028102222");

        Bank bank = new Bank();
        bank.addUser(firstUser);
        bank.addAccountToUser(firstUser, firsAccount);
//        bank.addAccountToUser(firstUser, secondAccount);

        boolean result = bank.transferMoney(firstUser, firsAccount, firstUser, secondAccount, 2500);
        boolean expected = false;

        assertThat(result, is(expected));
    }

}