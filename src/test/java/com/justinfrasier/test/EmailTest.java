package com.justinfrasier.test;

import com.justinfrasier.emailValidation.EmailChecker;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailTest {

    EmailChecker uut;
    @Before
    public void before(){
        uut = new EmailChecker();
    }

    @Test  // True
    public void emailCheckWithEmailAddress(){ assertTrue(uut.validate("cerealkiller1918@gmail.com")); }

    @Test  //False enter an email with the dot missing
    public void emailCheckWithoutDot(){ assertFalse(uut.validate("cerealkiller1918@gmailcom")); }

    @Test  //False enter an email without @ sign there
    public void emailCheckWithoutAt(){ assertFalse(uut.validate("cerealkiller1918gmail.com")); }

    @Test  //False
    public void emailCheckAtFirst(){assertFalse(uut.validate("@gmail.com"));}

    public void remove(){assertFalse(true);}
}
