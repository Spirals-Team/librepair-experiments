package ru.job4j.bank;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class AccountTest {

    @Test
    public void whenGetValue() {
        Account account = new Account(15, "123456789");
        assertThat(account.getValue(), is(15.0));
    }

    @Test
    public void whenGetRequisites() {
        Account account = new Account(0, "123456789");
        assertThat(account.getRequisites(), is("123456789"));
    }

    @Test
    public void whenSetValue() {
        Account account = new Account(0, "123");
        account.setValue(5000);
        assertThat(account.getValue(), is(5000.0));
    }

    @Test
    public void whenEqualsTruOnlyForRequisites() {
        Account accountOne = new Account(0, "12 78 00");
        Account accountTwo = new Account(10000, "12 78 00");
        assertTrue(accountOne.equals(accountTwo));
    }
}