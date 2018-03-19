package com.worldoftestcraft.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import java.sql.SQLException;

import com.worldoftestcraft.repository.*;

import org.junit.After;
import org.junit.Before;

@RunWith(JUnit4.class)
public class AccountTest {
    AccountRepository accountRepository;

    @Test
    public void getById() throws SQLException {
        int idToFind = 1;
        assertNotNull(accountRepository.getById(idToFind));
    }

    @Test
    public void getAll() throws SQLException {
        assertNotNull(accountRepository.getAll());
    }

    @Test
    public void addAccount() throws SQLException {
        Account account = new Account();
        account.setLogin("Glonfindel");
        account.setPassword("aaa");
        accountRepository.add(account);
        assertNotNull(accountRepository.getById(account.getId()));
    }
    
    @Test
    public void deleteAccount() throws SQLException {
        Account account = accountRepository.getById(3);
        accountRepository.delete(account);
        assertNull(accountRepository.getById(account.getId()).getLogin());
    }

    @Test
    public void updateAccount() throws SQLException {
        Account accountToTest = accountRepository.getById(2);
        Account account = new Account();
        account.setId(1);
        account.setLogin("Glonfindel");
        account.setPassword("aaa");
        int idToUpdate = 1;
        accountRepository.update(idToUpdate, account);
        assertEquals(accountRepository.getById(idToUpdate).getLogin(),
                accountRepository.getById(account.getId()).getLogin());
        assertNotEquals(accountToTest.login, accountRepository.getById(account.getId()).getLogin());
    }
    
    @Before
    public void initRepository() {
        accountRepository = AccountRepositoryFactory.getInstance();
        Account account1 = new Account();
        Account account2 = new Account();
        Account account3 = new Account();

        account1.setId(1);
        account1.setLogin("Glonfindel");
        account1.setPassword("aaa");

        account2.setId(2);
        account2.setLogin("Thravious");
        account2.setPassword("bbb");

        account3.setId(3);
        account3.setLogin("Arucane");
        account3.setPassword("ccc");

        accountRepository.add(account1);
        accountRepository.add(account2);
        accountRepository.add(account3);
    }

    @After
    public void dropRepository() {
        accountRepository.dropRepository();
    }
}
