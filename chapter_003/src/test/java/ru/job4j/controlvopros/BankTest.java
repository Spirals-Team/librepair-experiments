package ru.job4j.controlvopros;

import org.hamcrest.core.Is;
import org.junit.Test;
import ru.job4j.convertlistinmap.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class BankTest {

    /**
     * в этом методе мы должны добавлять юзеров в треемап, с пустыми аррайлистами
     */
    @Test
    public void addUsertest() {
        Bank bank = new Bank();
        ArrayList<Account> accountList = new ArrayList<>();
        User user1 = new User("Peter", 30, "Piter");
        User user2 = new User("Sasha", 31, "Novosib");
        bank.addUser(user1);
        bank.addUser(user2);
        TreeMap<User, ArrayList<Account>> expected = new TreeMap<>();
        expected.put(user1, new ArrayList<Account>());
        expected.put(user2, new ArrayList<Account>());
        assertThat(expected, Is.is(bank.getTreemap()));
    }

    /**
     * здесь мы в треемап находим нужного юзера и добавляем в его аррайлист аккаунты
     */
    @Test
    public void addUseraccounttest() {
        Bank bank = new Bank();
        ArrayList<Account> accountList = new ArrayList<>();
        Account account1 = new Account(654654, "one");
        Account account2 = new Account(32434432, "two");
        Account account3 = new Account(654654, "fri");
        Account account4 = new Account(654654, "foo");
        accountList.addAll(Arrays.asList(account1, account2, account3, account4));
        User user1 = new User("User1", 30, "Piter");
        User user2 = new User("User2", 31, "Novosib");
        System.out.println(user1 + " " + user1.hashCode());
        System.out.println(user2 + " " + user2.hashCode());
        bank.addUser(user1);
        bank.addUser(user2);

        bank.add(user2, account1);
        bank.add(user2, account2);
        bank.add(user2, account3);
        bank.add(user2, account4);
        TreeMap<User, ArrayList<Account>> expected = new TreeMap<>();
        expected.put(user1, new ArrayList<Account>());
        expected.put(user2, new ArrayList<Account>());
        expected.get(user2).add(account1);
        expected.get(user2).add(account2);
        expected.get(user2).add(account3);
        expected.get(user2).add(account4);
        assertThat(accountList, Is.is(bank.getAccounts(user2)));
    }

    /**
     * проверка метода удаления пользователя
     */
    @Test
    public void delete() {
        Bank bank = new Bank();
        User user1 = new User("Peter", 30, "Piter");
        User user2 = new User("Sasha", 31, "Novosib");
        bank.addUser(user1);
        bank.addUser(user2);
        bank.delete(user1);
        assertThat(null, Is.is(bank.getAccounts(user1)));
    }


    @Test
    public void deleteAccount() {
        Bank bank = new Bank();
        ArrayList<Account> accountList = new ArrayList<>();
        Account account1 = new Account(654654, "one");
        Account account2 = new Account(32434432, "two");
        Account account3 = new Account(654654, "fri");
        Account account4 = new Account(654654, "foo");
        accountList.addAll(Arrays.asList(account1, account2, account3));
        User user1 = new User("Peter", 30, "Piter");
        User user2 = new User("Sasha", 31, "Novosib");
        bank.addUser(user1);
        bank.addUser(user2);
        bank.add(user2, account1);
        bank.add(user2, account2);
        bank.add(user2, account3);
        bank.add(user2, account4);
        bank.deleteAccount(user2, account4);
        assertThat(accountList, Is.is(bank.getAccounts(user2)));

    }

    @Test
    public void transfer() {
        Bank bank = new Bank();
        ArrayList<Account> accountList = new ArrayList<>();
        Account account1 = new Account(654654, "one");
        Account account2 = new Account(32434432, "two");
        Account account3 = new Account(654654, "fri");
        Account account4 = new Account(654654, "foo");
        accountList.addAll(Arrays.asList(account1, account2, account3, account4));
        User user1 = new User("User1", 30, "Piter");
        User user2 = new User("User2", 31, "Novosib");
        bank.addUser(user1);
        bank.addUser(user2);
        bank.add(user1, account1);
        bank.add(user2, account2);
        boolean expected = true;
        boolean res = bank.transfer(user1, account1, user2, account2, 654653);
        System.out.println(res);
        assertThat(expected, Is.is(res));
    }
}