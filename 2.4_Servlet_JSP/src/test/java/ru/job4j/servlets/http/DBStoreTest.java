package ru.job4j.servlets.http;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

@PrepareForTest(ValidateService.class)
@RunWith(PowerMockRunner.class)
public class DBStoreTest {
    private DBStore store = mock(DBStore.class);
    private HelpForTesting hft;

    /**
     * Before.
     */
    @Before
    public void before() {
        Whitebox.setInternalState(DBStore.class, "INSTANCE", store);
        hft = new HelpForTesting();
    }

    /**
     * Test add.
     */
    @Test
    public void testAdd() {
        User user = new User(1, "test1", "", "", "", "", null);
        hft.add(user);
        verify(store, atLeastOnce()).add(user);

        hft.add(user);
        verify(store, times(2)).add(user);
    }

    /**
     * Test update
     */
    @Test
    public void testUpdate() {
        hft.update(null, null, null, null, null);
        verify(store, atLeastOnce()).update(null, null, null, null, null);
    }

    /**
     * Test findById.
     */
    @Test
    public void testFindById() {
        when(store.findById(1)).thenReturn(new User(1, "test1", "", "", "", "", null));
        when(store.findById(2)).thenReturn(new User(1, "test2", "", "", "", "", null));
        String expect1 = hft.findById(1).getName();
        String expect2 = hft.findById(2).getName();
        assertThat(expect1, is("test1"));
        assertThat(expect2, not("test1"));
    }

    /**
     * Test Delete.
     */
    @Test
    public void testDelete() {
        hft.delete(1);
        verify(store, atLeastOnce()).delete(1);

        hft.delete(1);
        verify(store, times(2)).delete(1);
    }

    /**
     * Class for current test.
     */
    private class HelpForTesting {
        private DBStore store = DBStore.getInstance();

        /**
         * add.
         * @param user user
         */
        private void add(User user) {
            store.add(user);
        }

        /**
         * Update.
         * @param login login
         * @param name name
         * @param email email
         * @param role role
         * @param password password
         */
        private void update(String login, String name, String email, String role, String password) {
            store.update(login, name, email, role, password);
        }

        /**
         * Delete.
         * @param id id
         */
        private void delete(int id) {
            store.delete(id);
        }

        /**
         * Find.
         * @param id id
         * @return user
         */
        private User findById(int id) {
            return store.findById(id);
        }
    }
}