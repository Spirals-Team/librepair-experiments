package io.joaopinheiro.userservice.unit.service;

import io.joaopinheiro.userservice.repository.UserRepository;
import io.joaopinheiro.userservice.service.UserService;
import io.joaopinheiro.userservice.service.errors.UserAlreadyExistsException;
import io.joaopinheiro.userservice.service.errors.UserNotFoundException;
import io.joaopinheiro.userservice.service.errors.UserUpdateErrorException;
import io.joaopinheiro.userservice.User;
import io.joaopinheiro.userservice.UserBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService service;

    @Before
    public void before(){
        userRepository = mock(UserRepository.class);
        service = new UserService(userRepository);
    }


    @Test
    public void getUserByID() {
        User result = new UserBuilder().build();

        given(userRepository.findById(result.getId())).willReturn(Optional.of(result));
        assertEquals(result, service.getUserByID(result.getId()));
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserByIDNotFound(){
        given(userRepository.findById(Long.MAX_VALUE)).willReturn(Optional.empty());
        service.getUserByID(Long.MAX_VALUE);
    }

    @Test (expected = UserAlreadyExistsException.class)
    public void createUserAlreadyExists() {
        User user = new UserBuilder().build();
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        service.createUser(user);
    }

    @Test
    public void createUser(){
        User user = new UserBuilder().build();
        given(userRepository.findById(user.getId())).willReturn(Optional.empty());
        given(userRepository.save(user)).willReturn(user);

        assertEquals(user, service.createUser(user));
    }

    @Test (expected = UserNotFoundException.class)
    public void updateUserNotFound() {
        User user = new UserBuilder().build();
        given(userRepository.findById(user.getId())).willReturn((Optional.empty()));
        service.updateUser(user, user.getId());
    }

    @Test (expected = UserUpdateErrorException.class)
    public void updateUserIdUpdate(){
        User user = new UserBuilder().build();
        given(userRepository.findById(Long.MAX_VALUE)).willReturn(Optional.of(user));
        service.updateUser(user, Long.MAX_VALUE);
    }

    @Test
    public void updateUser(){
        User oldUser = new UserBuilder().build();
        User newUser = new UserBuilder()
                .withEmail("updated@email.com")
                .withUsername("Updated Name").build();

        given(userRepository.findById(oldUser.getId())).willReturn(Optional.of(oldUser));
        given(userRepository.save(newUser)).willReturn(newUser);

        assertEquals(newUser, service.updateUser(newUser, oldUser.getId()));
    }

    @Test (expected = UserNotFoundException.class)
    public void deleteUserNotFound() {
        given(userRepository.findById(Long.MAX_VALUE)).willReturn(Optional.empty());
        service.deleteUser(Long.MAX_VALUE);
    }

    @Test
    public void emailValidationTest(){
        assertFalse(service.validateEmail("user@mail"));
        assertFalse(service.validateEmail("user@.com"));
        assertFalse(service.validateEmail("user.com"));
        assertFalse(service.validateEmail("user@mail.com1"));
        assertFalse(service.validateEmail("@mail.com"));
        assertFalse(service.validateEmail("user"));
        assertFalse(service.validateEmail("@"));

        assertTrue(service.validateEmail("user@mail.com"));
        assertTrue(service.validateEmail("user@mail.io"));
        assertTrue(service.validateEmail("user.user@mail.com"));
        assertTrue(service.validateEmail("user@mail.net"));
    }

}