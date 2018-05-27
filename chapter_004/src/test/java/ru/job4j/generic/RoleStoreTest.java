package ru.job4j.generic;

import org.junit.Test;
import java.util.ArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class RoleStoreTest {
    @Test
    public void whenAddRole() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("Admin"));
        roleStore.add(new Role("User"));
        roleStore.add(new Role("SC"));
        assertThat(roleStore.list.size(), is(3));
        assertThat(roleStore.list.get(0), is(new Role("Admin")));
        assertThat(roleStore.list.get(1), is(new Role("User")));
        assertThat(roleStore.list.get(2), is(new Role("SC")));
    }
    @Test
    public void whenDeleteRole() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("Admin"));
        roleStore.add(new Role("User"));
        roleStore.add(new Role("SC"));
        roleStore.delete("SC");
        assertThat(roleStore.list.size(), is(2));
        assertThat(roleStore.list.get(1), is(new Role("User")));
    }
    @Test
    public void whenReplaceRole() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("Admin"));
        roleStore.add(new Role("User"));
        roleStore.add(new Role("SC"));
        roleStore.replace("SC", new Role("Programmer"));
        assertThat(roleStore.list.get(2), is(new Role("Programmer")));
    }
    @Test
    public void whenFindByIdRole() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("Admin"));
        roleStore.add(new Role("User"));
        roleStore.add(new Role("SC"));
        assertThat(roleStore.findById("User"), is(new Role("User")));
    }
}