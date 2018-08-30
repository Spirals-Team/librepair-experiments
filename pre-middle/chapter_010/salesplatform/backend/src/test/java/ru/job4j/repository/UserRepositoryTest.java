package ru.job4j.repository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.BaseTest;
import ru.job4j.model.Role;
import ru.job4j.model.User;
import ru.job4j.service.UserService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserRepositoryTest extends BaseTest {

    @Autowired
    private UserRepository repo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private UserService userService;

    private User user;
    private Role role;

    @Before
    public void setUp() {
        role = roleRepo.findById(1L)
            .orElse(roleRepo.save(new Role(1L, "ROLE_USER")));
        user = new User().setId(1L)
            .setEmail("ivanov@email.com")
            .setPassw("1234")
            .setRole(role)
            .setPhone("555-55-55");
        user = repo.findById(1L).orElse(repo.save(user));
    }

    @Test
    public void findById() {
        User u = repo.findById(1L)
            .orElseThrow(() -> new IllegalStateException("User not found"));
        assertThat(u.getRole(), is(role));
        assertThat(u.getId(), is(1L));
    }

    @Test
    public void findByEmail() {
        User u = repo.findByEmailIgnoreCase("ivanov@email.com")
            .orElseThrow(() -> new IllegalStateException("User not found"));
        assertThat(u.getRole(), is(role));
        assertThat(u.getEmail(), is("ivanov@email.com"));
    }

    @Test
    public void findByEmailAndPassw() {
        User u = repo.findByEmailIgnoreCaseAndPassw("ivanov@email.com", "1234")
            .orElseThrow(() -> new IllegalStateException("User not found"));
        assertThat(u.getRole(), is(role));
        assertThat(u, is(user));
        assertThat(u.getEmail(), is("ivanov@email.com"));
        assertThat(u.getPassw(), is("1234"));
    }

    @Test
    public void findAll() {
        List<User> users = repo.findAll();
        assertThat(users.size(), greaterThanOrEqualTo(1));
        assertThat(users, hasItem(user));
    }

    @Test
    public void isCredentialUserExist() {
        assertTrue(userService.isCredential("ivanov@email.com", "1234"));
    }

    @Test
    public void isCredentialUserNonExist() {
        assertFalse(userService.isCredential("non@email.com", "1234"));
    }

//    @Test
//    public void create() {
//        User newUser = new User().setEmail("miller@email.com")
//            .setPassw("4321")
//            .setRole(new Role(2L, "GUEST"))
//            .setPhone("222-22-22");
//        Long id = new UserDaoImpl().create(newUser);
//        newUser.setId(id);
//        User savedUser = new UserDaoImpl().findOne(id);
//        assertThat(savedUser, is(newUser));
//        assertThat(savedUser.getRole().getType(), is("GUEST"));
//        assertThat(savedUser.getEmail(), is("miller@email.com"));
//    }
//
//    @Test
//    public void update() {
//        User newUser = new User().setEmail("miller@email.com")
//            .setPassw("4321")
//            .setRole(new Role(1L, "ROLE_USER"))
//            .setPhone("222-22-22");
//        Long id = new UserDaoImpl().create(newUser);
//        newUser.setId(id);
//        new UserDaoImpl().update(newUser.setEmail("moreau@email.com"));
//        User savedUser = new UserDaoImpl().findOne(id);
//        assertThat(savedUser, is(newUser));
//        assertThat(savedUser.getRole().getType(), is("ROLE_USER"));
//        assertThat(savedUser.getEmail(), is("moreau@email.com"));
//    }
//
//    @Test
//    public void updateAll() {
//        User newUser1 = new User().setEmail("fernandez@email.com")
//            .setPassw("4321")
//            .setRole(new Role(1L, "ROLE_USER"))
//            .setPhone("222-22-22");
//        newUser1.setId(new UserDaoImpl().create(newUser1));
//        User newUser2 = new User().setEmail("lopez@email.com")
//            .setPassw("4321")
//            .setRole(new Role(1L, "ROLE_USER"))
//            .setPhone("222-22-22");
//        newUser2.setId(new UserDaoImpl().create(newUser2));
//        List<User> changedUsers = Arrays.asList(newUser1.setEmail("rodriguez@email.com"),
//            newUser2.setRole(new Role(2L, "GUEST")));
//        new UserDaoImpl().updateAll(changedUsers);
//        assertThat(new UserDaoImpl().findOne(newUser1.getId()).getEmail(),
//            is("rodriguez@email.com"));
//        assertThat(new UserDaoImpl().findOne(newUser2.getId()).getEmail(),
//            is("lopez@email.com"));
//        assertThat(new UserDaoImpl().findOne(newUser1.getId()).getRole().getType(),
//            is("ROLE_USER"));
//        assertThat(new UserDaoImpl().findOne(newUser2.getId()).getRole().getType(),
//            is("GUEST"));
//    }
//
//    @Test
//    public void delete() {
//        User newUser = new User().setEmail("kowalski@email.com")
//            .setPassw("4321")
//            .setRole(new Role(1L, "ROLE_USER"))
//            .setPhone("333-33-33");
//        Long id = new UserDaoImpl().create(newUser);
//        newUser.setId(id);
//        new UserDaoImpl().delete(newUser);
//        User foundUser = new UserDaoImpl().findOne(id);
//        assertThat(foundUser, nullValue());
//    }
//
//    @Test
//    public void deleteById() {
//        User newUser = new User().setEmail("ruzicka@email.com")
//            .setPassw("4321")
//            .setRole(new Role(1L, "ROLE_USER"))
//            .setPhone("333-33-33");
//        Long id = new UserDaoImpl().create(newUser);
//        newUser.setId(id);
//        new UserDaoImpl().deleteById(id);
//        User savedUser = new UserDaoImpl().findOne(id);
//        assertThat(savedUser, nullValue());
//    }
}