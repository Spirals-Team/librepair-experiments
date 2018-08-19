package ua.epam.spring.hometask.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.EnumSet;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vladimir_Vysokomorny on 15-Jan-18.
 */
public class TestUser {

    private User user;

    @Before
    public void initUser(){
        user = new User();
    }

    @Test
    public void testNull(){
        assertEquals("", user.getRolesToString());
    }

    @Test
    public void testBookingManager(){
        user.setRoles(EnumSet.of(Role.BOOKING_MANAGER));
        assertEquals("BOOKING_MANAGER", user.getRolesToString());
    }
    @Test
    public void testRegisteredUser(){
        user.setRoles(EnumSet.of(Role.REGISTERED_USER));
        assertEquals("REGISTERED_USER", user.getRolesToString());
    }
    @Test
    public void testRegisteredAndBookingUser(){
        user.setRoles(EnumSet.of(Role.REGISTERED_USER, Role.BOOKING_MANAGER));
        assertEquals("BOOKING_MANAGER,REGISTERED_USER", user.getRolesToString());
    }
    @Test
    public void testBookingUserAndRegistered(){
        user.setRoles(EnumSet.of(Role.BOOKING_MANAGER, Role.REGISTERED_USER));
        assertEquals("BOOKING_MANAGER,REGISTERED_USER", user.getRolesToString());
    }

}
