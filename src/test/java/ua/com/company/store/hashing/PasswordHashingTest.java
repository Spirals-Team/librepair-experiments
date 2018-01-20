package ua.com.company.store.hashing;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Владислав on 23.12.2017.
 */
public class PasswordHashingTest {
    private PasswordHashing passwordHashing;
    @Before
    public void setUp() throws Exception {
      passwordHashing = PasswordHashing.getInstance();
    }

    @After
    public void tearDown() throws Exception {
     passwordHashing = null;
    }

    @Test
    public void hashingPassword() throws Exception {
       Assert.assertEquals(passwordHashing.hashingPassword("hello"),passwordHashing.hashingPassword("hello"));
    }

}