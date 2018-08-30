package ru.job4j.dao;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.DbConnector;
import ru.job4j.dto.UserDto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserDaoImplTest {

    private UserDto admin;
    private UserDao userDao;

    @Before
    public void setUp() {
        DbConnector connector = DbConnector.getInstance();
        connector.cleanDB();
        connector.initDB();
        Date date = Date.valueOf(LocalDate.now());
        admin = new UserDto(1L, "admin@email.com", "admin1234", 1L, 1L, date);
        userDao = new UserDaoImpl();
    }

    @Test
    public void findOne() {
        assertThat(userDao.findOne(1L), is(admin));
    }

    @Test
    public void findAll() {
        List<UserDto> users = new ArrayList<>();
        users.add(admin);
        List<UserDto> all = userDao.findAll();
        assertThat(all, hasSize(equalTo(1)));
        assertThat(all,
            containsInAnyOrder(users.toArray(new UserDto[0])));
    }

    @Test
    public void create() {
        UserDto testUser = new UserDto(2L, "test@email.com", "test1234", 3L,
            1L, Date.valueOf(LocalDate.now()));
        assertThat(userDao.create(testUser), is(2L));
        assertThat(userDao.findAll(), hasSize(equalTo(2)));
        UserDto newUser = userDao.findOne(2L);
        assertThat(newUser.getId(), is(testUser.getId()));
        assertThat(newUser.getEmail(), is(testUser.getEmail()));
        assertThat(newUser.getPassw(), is(testUser.getPassw()));
        assertThat(newUser.getRoleId(), is(testUser.getRoleId()));
        assertThat(newUser.getAddressId(), is(testUser.getAddressId()));
        assertThat(newUser.getCreateDate(), is(testUser.getCreateDate()));
    }

    @Test
    public void update() {
        Date createDate = Date.valueOf(LocalDate.now());
        UserDto testUser = new UserDto(1L, "test@email.com", "test1234", 3L,
            1L, createDate);
        UserDto user = userDao.findOne(1L);
        boolean result = userDao.update(testUser);
        UserDto updatedUser = userDao.findOne(1L);
        assertThat(result, is(true));
        assertThat(userDao.findAll(), hasSize(equalTo(1)));
        assertThat(user, not(updatedUser));
        assertThat(updatedUser.getId(), is(1L));
        assertThat(updatedUser.getEmail(), is("test@email.com"));
        assertThat(updatedUser.getPassw(), is("test1234"));
        assertThat(updatedUser.getRoleId(), is(3L));
        assertThat(updatedUser.getAddressId(), is(1L));
        assertThat(updatedUser.getCreateDate(), is(createDate));
    }

    @Test
    public void delete() {
        boolean result = userDao.delete(admin);
        UserDto user = userDao.findOne(admin.getId());
        assertThat(result, is(true));
        assertThat(userDao.findAll(), hasSize(equalTo(0)));
        assertThat(user.isEmpty(), is(true));
    }

    @Test
    public void deleteById() {
        boolean result = userDao.deleteById(admin.getId());
        UserDto user = userDao.findOne(admin.getId());
        assertThat(result, is(true));
        assertThat(userDao.findAll(), hasSize(equalTo(0)));
        assertThat(user.isEmpty(), is(true));
    }
}