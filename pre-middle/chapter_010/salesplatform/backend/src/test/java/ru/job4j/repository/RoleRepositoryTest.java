package ru.job4j.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.BaseTest;
import ru.job4j.model.Role;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class RoleRepositoryTest extends BaseTest {

    @Autowired
    private RoleRepository repo;

    @Test
    public void findOne() {
        Role role = repo.findById(1L)
            .orElseThrow(() ->
                new IllegalStateException("Role with id 1L not found"));
        assertThat(role.getType(), is("ROLE_USER"));
    }

    @Test
    public void findAll() {
        assertThat(repo.findAll(), hasSize(2));
    }

}