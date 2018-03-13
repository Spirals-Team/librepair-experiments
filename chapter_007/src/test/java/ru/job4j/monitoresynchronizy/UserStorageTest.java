package ru.job4j.monitoresynchronizy;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserStorageTest {

    @Test
    public void whenTransferMoneyFromOneUserToAnotherThenAmontIsChanged() {
        UserStorage storage = new UserStorage();

        User userPayer = new User(1, 100);
        User userGetter = new User(2, 200);

        storage.add(userPayer);
        storage.add(userGetter);
        storage.transfer(1, 2, 50);

        int result = userPayer.amount;
        int expected = 50;

        assertThat(expected, is(result));
    }

}
