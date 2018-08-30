package ru.job4j.dao;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.DbConnector;
import ru.job4j.model.Role;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RoleDaoImplTest {

    private Role admin;
    private RoleDao roleDao;

    @Before
    public void setUp() {
        DbConnector connector = DbConnector.getInstance();
        connector.cleanDB();
        connector.initDB();
        admin = new Role(1L, "ADMIN");
        roleDao = new RoleDaoImpl();
    }

    @Test
    public void findOne() {
        assertThat(roleDao.findOne(1L), is(admin));
    }

    @Test
    public void findAll() {
        List<Role> roles = new ArrayList<>();
        roles.add(admin);
        roles.add(new Role(2L, "MANDATOR"));
        roles.add(new Role(3L, "USER"));
        List<Role> all = roleDao.findAll();
        assertThat(all, hasSize(equalTo(3)));
        assertThat(all,
            containsInAnyOrder(roles.toArray(new Role[0])));
    }

    @Test
    public void create() {
        Role test = new Role("TEST");
        assertThat(roleDao.create(test), is(4L));
        assertThat(roleDao.findAll(), hasSize(equalTo(4)));
        assertThat(roleDao.findOne(4L).getType(), is(test.getType()));
    }

    @Test
    public void update() {
        Role user = roleDao.findOne(3L);
        boolean result = roleDao.update(new Role(3L, "GUEST"));
        Role guest = roleDao.findOne(3L);
        assertThat(result, is(true));
        assertThat(roleDao.findAll(), hasSize(equalTo(3)));
        assertThat(user, not(guest));
        assertThat(guest.getType(), is("GUEST"));
    }

    @Test
    public void delete() {
        boolean result = roleDao.delete(admin);
        Role role = roleDao.findOne(admin.getId());
        assertThat(result, is(true));
        assertThat(roleDao.findAll(), hasSize(equalTo(2)));
        assertThat(role.isEmpty(), is(true));
    }

    @Test
    public void deleteById() {
        boolean result = roleDao.deleteById(admin.getId());
        Role role = roleDao.findOne(admin.getId());
        assertThat(result, is(true));
        assertThat(roleDao.findAll(), hasSize(equalTo(2)));
        assertThat(role.isEmpty(), is(true));
    }
}