package  ru.job4j.multithreading.users;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.multithreading.users.exeptions.CanNotAddOrUpdateOrDeleteUserException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *Test.
 * @author Hincu Andrei (andreih1981@gmail.com) by 08.11.17;
 * @version $Id$
 * @since 0.1
 */
public class UserStorageTest {
    private UserStorage userStorage;
    @Before
    public void start() {
        userStorage = new UserStorage();
    }

    @Test(expected = CanNotAddOrUpdateOrDeleteUserException.class)
    public void addedTwoUsersWHisEqualsId() {
        User user1 = new User(1, 100);
        User user2 = new User(1, 200);
        userStorage.add(user1);
        userStorage.add(user2);
    }

    @Test(expected = CanNotAddOrUpdateOrDeleteUserException.class)
    public void whenUserNotFound() {
        User user1 = new User(1, 100);
        User user2 = new User(2, 200);
        userStorage.add(user1);
        userStorage.update(user2);
    }

    @Test
    public void whenUserWasUpdate() {
        User user1 = new User(1, 100);
        User user2 = new User(1, 200);
        userStorage.add(user1);
        userStorage.update(user2);
        assertThat(userStorage.getStorage().get(1), is(user2));
    }

    @Test
    public void whenTransferFromOneUserToSecondUser() throws InterruptedException {
        User user1 = new User(1, 100);
        User user2 = new User(2, 200);
        userStorage.add(user1);
        userStorage.add(user2);
        userStorage.transfer(1, 2, 50);
        assertThat(user1.getAmount(), is(50));
        assertThat(user2.getAmount(), is(250));
    }

    @Test
    public void whenUserWasDelete() throws Exception {
        User user1 = new User(1, 100);
        User user2 = new User(2, 200);
        userStorage.add(user1);
        userStorage.add(user2);
        userStorage.delete(user1);
        assertThat(userStorage.getStorage().containsKey(1), is(false));
    }

}