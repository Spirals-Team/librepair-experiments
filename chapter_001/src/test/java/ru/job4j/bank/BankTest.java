package ru.job4j.bank;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BankTest {

    // тестовое хранилище
    private Bank bank = new Bank();

    @Before
    /**
     * Метод, заплняющий тестовое хранилище данными для тестов
     */
    public void setUp() throws NoSuchUserException, AlreadyThereSuchUserException {
        bank.addUser(new User("vasja","ac 576435"));
        bank.addUser(new User("petya","as 234565"));
        bank.addUser(new User("ivan","tr 533476"));
        bank.addUser(new User("dima","bv 654365"));
        bank.addAccountToUser("as 234565", new Account("qaz876654322",23465.65d));
        bank.addAccountToUser("bv 654365", new Account("tre876675552",465.74d));
        bank.addAccountToUser("tr 533476", new Account("fds453654322",234.43d));
        bank.addAccountToUser("bv 654365", new Account("jhg876677000",4465.76d));
        bank.addAccountToUser("as 234565", new Account("ytr654654322",123465.11d));
        bank.addAccountToUser("bv 654365", new Account("nbv876632332",4659.76d));

    }

    @Test
    /**
     * Тест метода addUser()
     * Добавляем клиента и проверяем появился ли он в хранилище
     */
    public void addUser() throws AlreadyThereSuchUserException {
        bank.addUser(new User("igor","fd 998765"));
        assertThat(bank.getUsers().containsKey(new User("igor","fd 998765")), is(true));
    }

    @Test
    /**
     * Тест метода deleteUser()
     * Удаляет клиента и проверяем остался ли он в хранилище
     */
    public void deleteUser() throws NoSuchUserException{
        bank.deleteUser (new User("ivan","tr 533476"));
        assertThat(bank.getUsers().containsKey(new User("ivan","tr 533476")), is(false));
    }

    @Test
    /**
     * Тест метода addAccountToUser()
     * Добавляем счета клиента и проверяем появился ли один из них в хранилище
     */
    public void addAccountToUser() throws NoSuchUserException{
        bank.addAccountToUser("ac 576435", new Account("qaz876654311",65.65d));
        bank.addAccountToUser("ac 576435", new Account("tre873237332",465.65d));
        assertThat(bank.getUsers().get(new User("vasja","ac 576435")).contains(new Account("qaz876654311",65.65d)), is(true));
    }

    @Test
    /**
     * Тест метода deleteAccountFromUser()
     * Удаляем счет клиента и проверяем остался ли он в хранилище
     */
    public void deleteAccountFromUser() throws NoSuchUserException, NoSuchAccountException{
        bank.deleteAccountFromUser("bv 654365", new Account("jhg876677000",4465.76d));
        assertThat(bank.getUsers().get(new User("dima","bv 654365")).contains(new Account("jhg876677000",4465.76d)), is(false));
    }

    @Test
    /**
     * Тест метода getUserAccounts()
     * Получаем список счетов клиента и проверяем есть ли в нем конкретный счет
     */
    public void getUserAccounts() throws NoSuchUserException {
        assertThat(bank.getUserAccounts("bv 654365").contains(new Account("nbv876632332",4659.76d)), is(true));
    }

    @Test
    /**
     * Тест метода transferMoney()
     * Переводим 100 ед с одного счета на другой. Проверяем что счета изменились.
     */
    public void transferMoney() throws NoSuchUserException, NoSuchAccountException {
        boolean result;
        Account src;
        Account dst;
        bank.addAccountToUser("as 234565", new Account("ytr657781322",123.11d));
        bank.addAccountToUser("bv 654365", new Account("nbv876777332",46.76d));
        src = bank.findAccountByRequisite("ytr657781322");
        dst = bank.findAccountByRequisite("nbv876777332");
        result = bank.transferMoney("as 234565","ytr657781322","bv 654365","nbv876777332",100);
        assertThat(result && src.getValue()<24 && dst.getValue()>146, is(true));
    }

}