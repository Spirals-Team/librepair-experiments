package com.justinfrasier.test;

import com.justinfrasier.email.Email;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class sendEmailTest {

    private Email email;

    @Before
    public void before(){
        email = new Email();
    }

    @Test
    public void check_if_sent(){
        assertTrue(email.sendEmail("test"));
    }
}
