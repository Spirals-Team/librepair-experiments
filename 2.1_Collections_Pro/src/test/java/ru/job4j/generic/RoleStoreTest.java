package ru.job4j.generic;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class RoleStoreTest.
 */
public class RoleStoreTest {
    /**
     * Test add Role.
     *
     */
    @Test
    public void whenAddNewRoleThenArrayConta() {
        AbstractStore rs = new RoleStore(new SimpleArray(5));
        rs.add(new Role("1"));
        Role result = (Role) rs.getArray().get(0);
        assertThat(result.getId(), is("1"));
    }

    /**
     * Test update Role.
     */
    @Test
    public void whenUpdateOldRoleThenOldRoleReplacNewRole() {
        AbstractStore rs = new RoleStore(new SimpleArray(5));
        rs.add(new Role("1"));
        rs.add(new Role("2"));
        Role role = new Role("2");
        rs.update(role);
        Role result = (Role) rs.getArray().get(1);
        assertThat(result, is(role));
    }

    /**
     * Test delete Role.
     */
    @Test
    public void whenDeleteRoleThenNextRoleReplaceDeleteRole() {
        AbstractStore rs = new RoleStore(new SimpleArray(5));
        Role role1 = new Role("1");
        rs.add(role1);
        Role role2 = new Role("2");
        rs.add(role2);
        Role role3 = new Role("3");
        rs.add(role3);
        rs.delete(role2);
        Role result = (Role) rs.getArray().get(1);
        assertThat(result.getId(), is("3"));
    }
}