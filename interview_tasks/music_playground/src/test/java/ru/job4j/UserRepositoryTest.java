package ru.job4j;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.model.Address;
import ru.job4j.model.MusicType;
import ru.job4j.model.Role;
import ru.job4j.model.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserRepositoryTest {

    private UserRepository repo;
    private User admin;

    @Before
    public void setUp() {
        DbConnector connector = DbConnector.getInstance();
        connector.cleanDB();
        connector.initDB();
        repo = new UserRepository();
        admin = createAdmin();
    }

    private User createAdmin() {
        return new User()
            .setId(1L)
            .setEmail("admin@email.com")
            .setPassw("admin1234")
            .setRole(new Role(1L, "ADMIN"))
            .setAddress(new Address(1L, "Belgium", "Ghent", "Jakobijnenstraat 6"))
            .setGenres(Arrays.asList(new MusicType(1L, "RAP")))
            .setCreateDate(Date.valueOf(LocalDate.now()));
    }

    @Test
    public void findAll() {
        List<User> users = new ArrayList<>();
        users.add(admin);
        List<User> allUsers = repo.findAll();
        assertThat(allUsers, hasSize(equalTo(1)));
        assertThat(allUsers,
            containsInAnyOrder(users.toArray(new User[0])));
    }

    @Test
    public void create() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(admin);
        User newUser = new User()
            .setId(2L)
            .setEmail("test@email.com")
            .setPassw("test1234")
            .setRole(new Role(2L, "MANDATOR"))
            .setAddress(new Address(2L, "France", "Toulouse", "54 rue Peyrolieres"))
            .setGenres(Arrays.asList(new MusicType(5L, "CLASSICAL"),
                new MusicType(2L, "ROCK")))
            .setCreateDate(Date.valueOf(LocalDate.now()));
        expectedUsers.add(newUser);
        Long result = repo.create(newUser);
        List<User> allUsers = repo.findAll();
        assertThat(result, is(not(-1L)));
        assertThat(allUsers, hasSize(equalTo(2)));
        assertThat(allUsers,
            containsInAnyOrder(expectedUsers.toArray(new User[0])));
    }

    @Test
    public void findByAddress() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(admin);
        List<User> allUsers = repo.findByAddress(new Address(1L, "Belgium", "Ghent", "Jakobijnenstraat 6"));
        assertThat(allUsers, hasSize(equalTo(1)));
        assertThat(allUsers,
            containsInAnyOrder(expectedUsers.toArray(new User[0])));
    }

    @Test
    public void findByAddressPart() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(admin);
        List<User> allUsers = repo.findByAddressPart("Jakobij");
        assertThat(allUsers, hasSize(equalTo(1)));
        assertThat(allUsers,
            containsInAnyOrder(expectedUsers.toArray(new User[0])));
    }

    @Test
    public void findByRole() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(admin);
        List<User> allUsers = repo.findByRole(new Role(1L, "ADMIN"));
        assertThat(allUsers, hasSize(equalTo(1)));
        assertThat(allUsers,
            containsInAnyOrder(expectedUsers.toArray(new User[0])));
    }

    @Test
    public void findByMusicType() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(admin);
        List<User> allUsers = repo.findByMusicType(new MusicType(1L, "RAP"));
        assertThat(allUsers, hasSize(equalTo(1)));
        assertThat(allUsers,
            containsInAnyOrder(expectedUsers.toArray(new User[0])));
    }
}