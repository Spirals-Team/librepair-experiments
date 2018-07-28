package ru.job4j.bank;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class BankTest {
    @Test
    public void whenAddUserBankHasNewUser() {
        User user = new User("Yury", "AB204");
        Bank bank = new Bank();
        bank.addUser(user);
        assertThat(bank.findUserByPassport("AB204"), is(user));

    }

    @Test
    public void whenDeleteUserBankDoesNotHaveTheUser() {
        User user = new User("Yury", "AB204");
        Bank bank = new Bank();
        bank.addUser(user);
        bank.deleteUser(user);
        assertNull(bank.findUserByPassport("AB204"));
    }

    @Test
    public void whenAddAccountToUserThen() {
        User user = new User("Yury", "AB204");
        Account account1 = new Account(1000, "AB204_001");
        Account account2 = new Account(5000, "AB204_002");
        Bank bank = new Bank();
        bank.addUser(user);
        bank.addAccountToUser("AB204", account1);
        bank.addAccountToUser("AB204", account2);
        assertThat(bank.getUserAccounts("AB204").get(0), is(account1));
        assertThat(bank.getUserAccounts("AB204").get(1), is(account2));
    }

    @Test
    public void whenDeleteAccountFromUserThenUserDoesNotHaveTheAccount() {
        User user = new User("Yury", "AB204");
        Account account1 = new Account(1000, "AB204_001");
        Account account2 = new Account(5000, "AB204_002");
        Bank bank = new Bank();
        bank.addUser(user);
        bank.addAccountToUser("AB204", account1);
        bank.addAccountToUser("AB204", account2);
        bank.deleteAccountFromUser("AB204", account1);
        assertThat(bank.getUserAccounts("AB204").get(0), is(account2));
    }

    @Test
    public void whenTransferMoneyFromOneAccountToOtherAccountTheSameUser() {
        User user = new User("Yury", "AB204");
        Bank bank = new Bank();
        bank.addUser(user);
        bank.addAccountToUser("AB204", new Account(1000.0, "AB204_001"));
        bank.addAccountToUser("AB204", new Account(1000.0, "AB204_002"));
        bank.transferMoney("AB204", "AB204_001", "AB204", "AB204_002", 500.5);

        assertThat(bank.getUserAccounts("AB204").get(0).getValue(), is(499.5));
        assertThat(bank.getUserAccounts("AB204").get(1).getValue(), is(1500.5));
    }

    @Test
    public void whenTransferMoneyFromOneAccountToOtherAccountTheDifferentUser() {
        User user1 = new User("Yury", "AB204");
        User user2 = new User("Kirill", "AB303");
        Bank bank = new Bank();
        bank.addUser(user1);
        bank.addUser(user2);
        bank.addAccountToUser("AB204", new Account(1000.0, "AB204_001"));
        bank.addAccountToUser("AB303", new Account(1000.0, "AB303_001"));
        bank.transferMoney("AB204", "AB204_001", "AB303", "AB303_001", 999.5);

        assertThat(bank.getUserAccounts("AB204").get(0).getValue(), is(0.5));
        assertThat(bank.getUserAccounts("AB303").get(0).getValue(), is(1999.5));
    }

    @Test
    public void whenTransferMoneyFromOneAccountWichDoesNotExistToOtherAccountTheSameUser() {
        User user = new User("Yury", "AB204");
        Bank bank = new Bank();
        bank.addUser(user);
        bank.addAccountToUser("AB204", new Account(1000.0, "AB204_001"));
        assertThat(bank.transferMoney("AB204", "AB204_002", "AB204", "AB204_001", 500.0), is(false));
    }


    @Test
    public void whenTransferMoneyFromOneAccountToOtherAccountWhichDoesNotExist() {
        User user = new User("Yury", "AB204");
        Bank bank = new Bank();
        bank.addUser(user);
        bank.addAccountToUser("AB204", new Account(300.0, "AB204_001"));
        assertThat(bank.transferMoney("AB204", "AB204_001", "AB204", "AB204_002", 100.0), is(false));
    }

    @Test
    public void whenTransferMoneyFromOneAccountWichHasLessMoneyThenAmountToOtherAccountTheSameUser() {
        User user = new User("Yury", "AB204");
        Bank bank = new Bank();
        bank.addUser(user);
        bank.addAccountToUser("AB204", new Account(300.0, "AB204_001"));
        bank.addAccountToUser("AB204", new Account(1000.0, "AB204_002"));
        assertThat(bank.transferMoney("AB204", "AB204_001", "AB204", "AB204_002", 500.0), is(false));
    }

    @Test
    public void whenTransferMoneyFromOneAccountWichHasNegativeBalanceToOtherAccount() {
        User user = new User("Yury", "AB204");
        Bank bank = new Bank();
        bank.addUser(user);
        bank.addAccountToUser("AB204", new Account(-300.0, "AB204_001"));
        bank.addAccountToUser("AB204", new Account(1000.0, "AB204_002"));
        assertThat(bank.transferMoney("AB204", "AB204_001", "AB204", "AB204_002", 100.0), is(false));
    }
}
