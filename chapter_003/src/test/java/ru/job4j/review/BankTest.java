package ru.job4j.review;

import org.junit.Test;
import ru.job4j.user.User;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BankTest {
    @Test
    public void whenMapThenAddUser() {
        Bank bank = new Bank();
        User daniil = new User("Daniil", "1852638");
        User ivan = new User("Ivan", "1235696");
        Account accountDaniil = new Account(10000, "123456");
        Account accountDaniil2 = new Account(5000, "654321");
        Account accountIvan = new Account(7000, "3214566");
        bank.addUser(daniil);
        bank.addUser(ivan);
        bank.add(daniil, accountDaniil);
        bank.add(daniil, accountDaniil2);
        bank.add(ivan, accountIvan);
        assertThat(bank.transfer(daniil, accountDaniil, ivan, accountIvan, 500), is(true));
    }
}