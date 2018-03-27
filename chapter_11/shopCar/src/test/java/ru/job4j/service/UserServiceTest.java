package ru.job4j.service;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.model.Advert;
import ru.job4j.model.User;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 16.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class UserServiceTest {

    private UserService userService;
    @Before
    public void init() {
        userService = new UserService();
    }

    @Test
    public void whenUserWasAdd() {
        User user = new User("Andrei", "email@ttt.com", "123458", 1234586);
        Advert advert = new Advert();
        List<Advert> adverts = Arrays.asList(advert);
        user.setAdverts(adverts);
        userService.saveUser(user);
        assertThat("Andrei", is(userService.getAllUsers().get(0).getLogin()));
    }

    @Test
    public void whenUserWasUpdate() {
        User user = new User("Andrei", "email@ttt.com", "123458", 1234586);
        Long id = userService.saveUser(user);
        user.setId(id);
        user.setLogin("Petr");
        userService.updateUser(user);
        assertThat("Petr", is(userService.getAllUsers().get(0).getLogin()));
    }

    @Test
    public void whenUserWasFind() {
        User user = new User("Andrei", "email@ttt.com", "123458", 1234586);
        Long id = userService.saveUser(user);
        User user1 = userService.getUserById(id);
        assertThat(user1.getId(), is(user.getId()));
    }
}