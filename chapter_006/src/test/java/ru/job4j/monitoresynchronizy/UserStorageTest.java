package ru.job4j.monitoresynchronizy;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UserStorageTest {
    @Test
    public void whenAddElementInStorageThenElementAdded() {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 100));
        assertThat(storage.getStorage().get(1).getAmount(), is(100));
    }
    @Test
    public void whenUpdateElementInStorageThenElementUpdated() {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 100));
        storage.update(new User(1, 200));
        assertThat(storage.getStorage().get(1).getAmount(), is(200));
    }
    @Test
    public void whenDeleteElementInStorageThenElementDeleted() {
        UserStorage storage = new UserStorage();
        User user = new User(1, 100);
        storage.add(user);
        storage.delete(user);
        assertThat(storage.getStorage().get(user.getId()) == null, is(true));
    }
    @Test
    public void whenTransferThenAmountHasChanged() {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 100));
        storage.add(new User(2, 200));
        storage.transfer(1, 2, 50);
        assertThat(storage.getStorage().get(2).getAmount(), is(250));
    }
}