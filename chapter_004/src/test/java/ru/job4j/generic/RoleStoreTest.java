package ru.job4j.generic;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RoleStoreTest {
    @Test
    public void whenAdd() {
        RoleStore<Role> store = new RoleStore();
        Role role = new Role("one");

        store.add(role);

        assertThat(store.findById("one"), is(role));
    }

    @Test
    public void whenReplace() {
        RoleStore<Role> store = new RoleStore();
        Role role1 = new Role("one");
        Role role2 = new Role("five");
        Role role3 = new Role("three");

        store.add(role1);
        store.add(role2);
        store.add(role3);
        store.replace(role2.getId(), new Role("two"));

        assertThat(store.findById("two"), is(store.get(1)));
    }

    @Test
    public void whenDeleteSecondElement() {
        RoleStore<Role> store = new RoleStore();
        Role role1 = new Role("first");
        Role role2 = new Role("five");
        Role role3 = new Role("three");

        store.add(role1);
        store.add(role2);
        store.add(role3);
        store.delete("five");

        assertThat(store.findById("three"), is(store.get(1)));
    }

    @Test
    public void whenDelete() {
        RoleStore<Role> store = new RoleStore();
        Role role1 = new Role("first");
        Role role2 = new Role("two");

        store.add(role1);
        store.add(role2);

        assertThat(store.findById("two"), is(store.get(1)));
    }
}
