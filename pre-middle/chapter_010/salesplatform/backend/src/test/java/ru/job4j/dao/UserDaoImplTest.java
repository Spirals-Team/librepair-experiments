package ru.job4j.dao;

import org.junit.Test;
import ru.job4j.model.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

public class UserDaoImplTest {

    @Test
    public void findOne() {
        User user = new UserDaoImpl().findOne(1L);
        assertThat(user.getRole().getType(), is("USER"));
    }

    @Test
    public void findAll() {
        assertThat(new UserDaoImpl().findAll().size(), greaterThanOrEqualTo(1));
    }

    @Test
    public void create() {
        User user = new User().setEmail("ivanov2@email.com")
            .setPassw("1234")
            .setRole(new RoleDaoImpl().findOne(1L))
            .setPhone("555-55-55");
        Long id = new UserDaoImpl().create(user);
        user.setId(id);
        User savedUser = new UserDaoImpl().findOne(id);
        assertThat(savedUser, is(user));
    }

    @Test
    public void update() {
    }

    public void updateAll() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void deleteById() {
    }
}