package ru.job4j.bank;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BankSystemTest {

    @Test
    public void addUserTest() {
        BankSystem bankSystem = new BankSystem();
        bankSystem.addUser(new User("Elon Mask", "123"));
        assertThat("Elon Mask", is(bankSystem.getUser("123").getName()));
    }

    @Test
    public void deleteUserTest() {
        BankSystem bankSystem = new BankSystem();
        bankSystem.addUser(new User("Elon Mask", "123"));
        bankSystem.deleteUser(bankSystem.getUser("123"));
        assertThat(0, is(bankSystem.getAllUser().size()));
    }

    @Test
    public void addAccountToUserTest() {
        BankSystem bankSystem = new BankSystem();
        bankSystem.addUser(new User("Elon Mask", "123"));
        bankSystem.addAccountToUser("123", new Account(10000000, "Money"));
        assertThat("Money", is(bankSystem.getUserAccount("123").get(0).getRequisites()));
    }

    @Test
    public void deleteAccountFromUser() {
        BankSystem bankSystem = new BankSystem();
        Account accountMasc = new Account(10000000, "Money");
        bankSystem.addUser(new User("Elon Mask", "123"));
        bankSystem.addAccountToUser("123", accountMasc);
        bankSystem.deleteAccountFromUser("123", accountMasc);
        assertThat(0, is(bankSystem.getUserAccount("123").size()));
    }

    @Test
    public void getUserAccountTest() {
        BankSystem bankSystem = new BankSystem();
        bankSystem.addUser(new User("Elon Mask", "123"));
        bankSystem.addAccountToUser("123", new Account(123, "qwe"));
        bankSystem.addAccountToUser("123", new Account(124, "asd"));
        assertThat(2, is(bankSystem.getUserAccount("123").size()));
    }

    @Test
    public void transferMoneyTest() {
        BankSystem bankSystem = new BankSystem();
        bankSystem.addUser(new User("Bell Gets", "BellPassport"));
        bankSystem.addUser(new User("Elon Mask", "ElonPassport"));
        bankSystem.addAccountToUser("BellPassport", new Account(1000, "BellRequsites"));
        bankSystem.addAccountToUser("ElonPassport", new Account(500, "ElonRequsites"));
        bankSystem.transferMoney("BellPassport", "BellRequsites", "ElonPassport", "ElonRequsites", 1);
        assertThat(999.0, is(bankSystem.getUserAccount("BellPassport").get(0).getValue()));
        assertThat(501.0, is(bankSystem.getUserAccount("ElonPassport").get(0).getValue()));
    }
}