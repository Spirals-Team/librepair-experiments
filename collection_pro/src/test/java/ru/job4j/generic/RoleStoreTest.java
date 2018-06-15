package ru.job4j.generic;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.hamcrest.core.Is.is;

public class RoleStoreTest {
    @Test
    public void whenAddThatNewSimpleArrayRoleElement() {
        RoleStore store = new RoleStore(2);
        Role role = new Role("1");
        Role test = new Role("2");
        store.add(role);
        store.add(test);
        assertThat(store.findVyId("2"), is(test));
        assertThat(store.delete("2"), is(true));
        assertThat(store.replace("2"), is(true));
        assertThat(store.replace("3"), is(false));
    }


}