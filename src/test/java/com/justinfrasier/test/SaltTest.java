package com.justinfrasier.test;

import com.justinfrasier.password.Salt;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SaltTest {

    Salt salt;

    @Before
    public void before(){ salt = new Salt(); }

    @Test
    public void testSaltGenerate_1(){assertNotEquals(salt.generate(),salt.generate());}

    @Test
    public void testSaltGenerate_2(){assertNotEquals(salt.generate(),salt.generate());}

    @Test
    public void testSaltGenerate_3(){assertNotEquals(salt.generate(),salt.generate());}

    @Test
    public void testSaltGenerate_4(){assertNotEquals(salt.generate(),salt.generate());}

    @Test
    public void testSaltGenerate_5(){assertNotEquals(salt.generate(),salt.generate());}

}
