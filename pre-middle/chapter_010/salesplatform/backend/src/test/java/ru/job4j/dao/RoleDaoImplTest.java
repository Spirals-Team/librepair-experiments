package ru.job4j.dao;

import org.junit.Test;
import ru.job4j.model.Role;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class RoleDaoImplTest {

    @Test
    public void findOne() {
        Role role = new RoleDaoImpl().findOne(1L);
        assertThat(role.getType(), is("USER"));
    }

    @Test
    public void findAll() {
        assertThat(new RoleDaoImpl().findAll(), hasSize(2));
    }
}