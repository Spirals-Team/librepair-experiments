package tech.spring.structure.auth.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserTest {

    @Test
    public void testNewUser() {
        User user = new User("bborring", "HelloWorld123!", Role.ROLE_USER);
        assertNotNull("Unable to create user!", user);
        assertTrue("User is inactive!", user.isActive());
        assertTrue("User is disabled!", user.isEnabled());
        assertEquals("User has incorrect username!", "bborring", user.getUsername());
        assertEquals("User has incorrect role!", Role.ROLE_USER, user.getRole());
        assertEquals("User has incorrect password!", "HelloWorld123!", user.getPassword());
        assertNotNull("User does not have a timestamp!", user.getTimestamp());
    }

    @Test
    public void testNewUserDisabled() {
        User user = new User("bborring", "HelloWorld123!", Role.ROLE_USER, false);
        assertNotNull("Unable to create user!", user);
        assertTrue("User is inactive!", user.isActive());
        assertFalse("User is enabled!", user.isEnabled());
        assertEquals("User has incorrect username!", "bborring", user.getUsername());
        assertEquals("User has incorrect role!", Role.ROLE_USER, user.getRole());
        assertEquals("User has incorrect password!", "HelloWorld123!", user.getPassword());
        assertNotNull("User does not have a timestamp!", user.getTimestamp());
    }

    @Test
    public void testNewUserFromUser() {
        User user = new User(new User("bborring", "HelloWorld123!", Role.ROLE_USER));
        assertNotNull("Unable to create user!", user);
        assertTrue("User is inactive!", user.isActive());
        assertTrue("User is disabled!", user.isEnabled());
        assertEquals("User has incorrect username!", "bborring", user.getUsername());
        assertEquals("User has incorrect role!", Role.ROLE_USER, user.getRole());
        assertEquals("User has incorrect password!", "HelloWorld123!", user.getPassword());
        assertNotNull("User does not have a timestamp!", user.getTimestamp());
    }

    @Test
    public void testSetUsername() {
        User user = new User("bborring", "HelloWorld123!", Role.ROLE_USER, false);
        user.setUsername("eexciting");
        assertEquals("User did not set username!", "eexciting", user.getUsername());
    }

    @Test
    public void testSetOldPassword() {
        User user = new User("bborring", "ANewPassword321#", Role.ROLE_USER, false);
        user.setOldPasswords(new ArrayList<String>(Arrays.asList(new String[] { "HelloWorld123!", "HelloWorld123~" })));
        assertEquals("User did not set old passwords!", 2, user.getOldPasswords().size());
        assertEquals("User did not set correct old password!", "HelloWorld123!", user.getOldPasswords().get(0));
        assertEquals("User did not set correct old password!", "HelloWorld123~", user.getOldPasswords().get(1));
    }

    @Test
    public void testSetRole() {
        User user = new User("bborring", "HelloWorld123!", Role.ROLE_USER, false);
        user.setRole(Role.ROLE_ADMIN);
        assertEquals("User did not set role!", Role.ROLE_ADMIN, user.getRole());
    }

    @Test
    public void testSetEnabled() {
        User user = new User("bborring", "HelloWorld123!", Role.ROLE_USER, false);
        user.setEnabled(true);
        assertTrue("User did not enabled!", user.isEnabled());
    }

    @Test
    public void testSetActive() {
        User user = new User("bborring", "HelloWorld123!", Role.ROLE_USER);
        user.setActive(false);
        assertFalse("User did not deactivate!", user.isActive());
    }

}
