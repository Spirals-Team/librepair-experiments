package ru.job4j.threads;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserStorageTest {
    private class ThreadUsers extends Thread {
        private final UserStorage stoge;

        private ThreadUsers(final UserStorage stoge) {
            this.stoge = stoge;
        }

        @Override
        public void run() {
            stoge.add(new User(1, 100));
            stoge.add(new User(2, 200));
            stoge.transfer(1, 2, 50);
        }
    }
    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        //Создаем счетчик.
        final UserStorage stoge = new UserStorage();
        //Создаем нити.
        Thread first = new ThreadUsers(stoge);
        Thread second = new ThreadUsers(stoge);
        //Запускаем нити.
        first.start();
        second.start();
        //Заставляем главную нить дождаться выполнения наших нитей.
        first.join();
        second.join();
        //Проверяем результат.
        Assert.assertThat(stoge.getAmount(1), is(0));
        Assert.assertThat(stoge.getAmount(2), is(300));
    }
}