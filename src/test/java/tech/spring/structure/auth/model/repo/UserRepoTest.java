package tech.spring.structure.auth.model.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import tech.spring.structure.auth.exception.InvalidPasswordException;
import tech.spring.structure.auth.model.Role;
import tech.spring.structure.auth.model.User;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testCreate() {
        long initialUserRepoCount = userRepo.count();
        User user = userRepo.create(new User("ssam", "HelloWorld123!", Role.ROLE_USER));
        assertNotNull("Unable to create user!", user);
        assertTrue("User is inactive!", user.isActive());
        assertTrue("User is disabled!", user.isEnabled());
        assertEquals("User has incorrect username!", "ssam", user.getUsername());
        assertEquals("User has incorrect role!", Role.ROLE_USER, user.getRole());
        assertTrue("User has incorrect password!", passwordEncoder.matches("HelloWorld123!", user.getPassword()));
        assertEquals("Incorrect number of users in repo!", initialUserRepoCount + 1, userRepo.count());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testUsernameMinLength() {
        userRepo.create(new User("bu", "HelloWorld123!", Role.ROLE_USER));
    }

    @Test(expected = ConstraintViolationException.class)
    public void testUsernameMaxLength() {
        userRepo.create(new User("thisisareallyreallyreallyreallyreallyreallylongusername", "HelloWorld123!", Role.ROLE_USER));
    }

    @Test(expected = ConstraintViolationException.class)
    public void testUsernameAlphanumericOnly() {
        userRepo.create(new User("bad-username", "HelloWorld123!", Role.ROLE_USER));
    }

    @Test(expected = InvalidPasswordException.class)
    public void testPasswordMinLength() {
        userRepo.create(new User("password", "invalid", Role.ROLE_USER));
    }

    @Test(expected = InvalidPasswordException.class)
    public void testPasswordMaxLength() {
        userRepo.create(new User("password", "thisisareallyreallyreallyreallyreallyreallylongpassword", Role.ROLE_USER));
    }

    @Test(expected = InvalidPasswordException.class)
    public void testPasswordMissingUppercaseLetter() {
        userRepo.create(new User("password", "invalid123~", Role.ROLE_USER));
    }

    @Test(expected = InvalidPasswordException.class)
    public void testPasswordMissingLowercaseLetter() {
        userRepo.create(new User("password", "INVALID123~", Role.ROLE_USER));
    }

    @Test(expected = InvalidPasswordException.class)
    public void testPasswordMissingNumber() {
        userRepo.create(new User("password", "Invalid~", Role.ROLE_USER));
    }

    @Test(expected = InvalidPasswordException.class)
    public void testPasswordMissingSpecialCharacter() {
        userRepo.create(new User("password", "Invalid123", Role.ROLE_USER));
    }

    @Test
    public void testRead() {
        User user = userRepo.create(new User("ddonald", "HelloWorld123!", Role.ROLE_USER));
        Long id = user.getId();
        Optional<User> userRead = userRepo.read(id);
        assertTrue("Unable to read user!", userRead.isPresent());
        assertTrue("User is inactive!", userRead.get().isActive());
        assertTrue("User is disabled!", userRead.get().isEnabled());
        assertEquals("User has incorrect username!", "ddonald", userRead.get().getUsername());
        assertEquals("User has incorrect role!", Role.ROLE_USER, userRead.get().getRole());
        assertTrue("User has incorrect password!", passwordEncoder.matches("HelloWorld123!", userRead.get().getPassword()));
    }

    @Test
    public void testUpdate() {
        User user = userRepo.create(new User("ccathy", "HelloWorld123!", Role.ROLE_USER));
        user.setActive(false);
        user.setEnabled(false);
        user.setRole(Role.ROLE_ADMIN);
        user.setPassword(passwordEncoder.encode("ANewPassword321~"));
        user = userRepo.update(user);
        assertFalse("User is still active!", user.isActive());
        assertFalse("User is still enabled!", user.isEnabled());
        assertEquals("User role did not update!", Role.ROLE_ADMIN, user.getRole());
        assertTrue("User password did not update!", passwordEncoder.matches("ANewPassword321~", user.getPassword()));
    }

    @Test
    public void testDelete() {
        long initialUserRepoCount = userRepo.count();
        User user = userRepo.create(new User("iian", "HelloWorld123!", Role.ROLE_USER));
        assertEquals("Incorrect number of users in repo!", initialUserRepoCount + 1, userRepo.count());
        userRepo.delete(user);
        assertFalse("User was not deleted!", userRepo.findByUsername("iian").isPresent());
        assertEquals("Incorrect number of users in repo!", initialUserRepoCount, userRepo.count());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testDuplicate() {
        userRepo.create(new User("ttom", "HelloWorld123!", Role.ROLE_USER));
        userRepo.create(new User("ttom", "HelloWorld123!", Role.ROLE_USER));
    }

    @Test
    public void testEquals() {
        User firstUser = new User("first", "HelloWorld123!", Role.ROLE_USER);
        User secondUser = new User("second", "HelloWorld123!", Role.ROLE_USER);
        assertTrue("User does not equal same user!", firstUser.equals(firstUser));
        assertTrue("Two users without id do not equal!", firstUser.equals(secondUser));
        User firstCreated = userRepo.create(firstUser);
        Optional<User> firstFound = userRepo.findByUsername("first");
        User secondCreated = userRepo.create(secondUser);
        assertTrue("User created does not equal same user found!", firstCreated.equals(firstFound.get()));
        assertFalse("Two users equal when they shouldn't!", firstCreated.equals(secondCreated));
    }

    @Test
    public void testHashCode() {
        User hash = userRepo.create(new User("hash", "HelloWorld123!", Role.ROLE_USER));
        assertNotNull("User did not have a hashcode!", hash.hashCode());
    }

    @Test
    public void testCompareTo() {
        User lesser = userRepo.create(new User("lesser", "HelloWorld123!", Role.ROLE_USER));
        User greater = userRepo.create(new User("greater", "HelloWorld123!", Role.ROLE_USER));
        User same = userRepo.create(new User("same", "HelloWorld123!", Role.ROLE_USER));
        assertEquals("Lesser user was greater than greater user!", -1, lesser.compareTo(greater));
        assertEquals("Greater user was lesser than lesser user!", 1, greater.compareTo(lesser));
        assertEquals("Same user was not the same as same user!", 0, same.compareTo(same));
    }

}
