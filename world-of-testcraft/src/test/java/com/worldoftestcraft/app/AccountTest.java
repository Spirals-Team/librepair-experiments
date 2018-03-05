package com.worldoftestcraft.app;

import org.junit.Test;
import static org.junit.Assert.*;

import com.worldoftestcraft.repository.AccountRepository;
import com.worldoftestcraft.repository.AccountRepositoryFactory;

import org.junit.Before;

public class AccountTest {
    AccountRepository accountRepository;

    @Test
    public void getById() {
        int idToFind = 1;
        assertNotNull(accountRepository.getById(idToFind));
    }

    @Test
    public void getAll() {
        assertNotNull(accountRepository.getAll());
    }

    @Test
    public void addAccount() {
        Account account = new Account();
        account.setId(1);
        account.setLogin("Glonfindel");
        account.setPassword("aaa");
        accountRepository.add(account);
        assertNotNull(accountRepository.getById(account.getId()));

    }

    @Test
    public void deleteAccount() {
        Account account = accountRepository.getById(1);
        accountRepository.delete(account);
        if (accountRepository.getAll().size() > 0) {
            assertNotNull(accountRepository.getAll());
        } else {
            assertNull(accountRepository.getById(account.getId()));
        }
    }

    @Test
    public void updateAccount() {
        Account account = new Account();
        account.setId(1);
        account.setLogin("Glonfindel");
        account.setPassword("aaa");
        int idToUpdate = 1;
        accountRepository.update(idToUpdate, account);
        assertEquals(accountRepository.getById(idToUpdate).getLogin(), account.getLogin());

        for (Account accountFromList : accountRepository.getAll()) {
            if (accountFromList.getId() == idToUpdate) {
                assertNotEquals(accountFromList.getLogin(), account.getLogin());
            }
        }
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
}
