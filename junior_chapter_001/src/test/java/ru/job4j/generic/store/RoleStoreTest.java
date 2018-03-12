package ru.job4j.generic.store;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.generic.base.Role;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RoleStoreTest {
    private RoleStore rs;
    private Role roleOne = new Role("1");
    private Role roleTwo = new Role("2");
    private Role roleThree = new Role("3");

    @Before
    public void setUp() {
        rs = new RoleStore(10);
    }

    @Test
    public void whenAddThreeRoles() {
        rs.add(roleOne);
        rs.add(roleTwo);
        rs.add(roleThree);
        assertThat(rs.findById("0"), is(roleOne));
        assertThat(rs.findById("1"), is(roleTwo));
        assertThat(rs.findById("2"), is(roleThree));
    }

    @Test
    public void whenAddThreeRolesAndDeleteSecond() {
        rs.add(roleOne);
        rs.add(roleTwo);
        rs.add(roleThree);
        assertThat(rs.delete("1"), is(true));
        assertThat(rs.findById("0"), is(roleOne));
        assertThat(rs.findById("1"), is(roleThree));
    }

    @Test
    public void whenDeleteNullElements() {
        assertThat(rs.delete("0"), is(false));
    }

    @Test
    public void replaceRole() {
        rs.add(roleOne);
        assertThat(rs.findById("0"), is(roleOne));
        rs.replace("0", roleTwo);
        assertThat(rs.findById("0"), is(roleTwo));
    }
}