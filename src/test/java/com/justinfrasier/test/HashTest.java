package com.justinfrasier.test;

import com.justinfrasier.password.HashPassword;
import com.justinfrasier.password.Salt;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class HashTest {

    HashPassword uut;
    @Before
    public void before(){
        uut = new HashPassword();
    }

    @Test
    public void hashPasswordTest1(){
        assertNotEquals(uut.get_SHA_512_SecurePassword("test",new Salt().generate()),
                uut.get_SHA_512_SecurePassword("test",new Salt().generate()));
    }

    @Test
    public void hashPasswordTest2(){
        assertNotEquals(uut.get_SHA_512_SecurePassword("test",new Salt().generate()),
                uut.get_SHA_512_SecurePassword("test",new Salt().generate()));
    }

    @Test
    public void hashPasswordTest3(){
        assertNotEquals(uut.get_SHA_512_SecurePassword("test",new Salt().generate()),
                uut.get_SHA_512_SecurePassword("test",new Salt().generate()));
    }

    @Test
    public void hashPasswordTest4(){
        assertNotEquals(uut.get_SHA_512_SecurePassword("test",new Salt().generate()),
                uut.get_SHA_512_SecurePassword("test",new Salt().generate()));
    }

    @Test
    public void hashPasswordTest5(){
        assertNotEquals(uut.get_SHA_512_SecurePassword("test",new Salt().generate()),
                uut.get_SHA_512_SecurePassword("test",new Salt().generate()));
    }

}
