package ru.job4j.list;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

import ru.job4j.generic.*;

public class SimpleQueueTest {

    @Test
    public void whenPushTwoObjectsToSimpleStackThenOneCanPollFirstElement() {

        SimpleQueue simpleQueue = new SimpleQueue();

        User user1 = new User("1234");
        User user2 = new User("2222");
        User user3 = new User("3333");


        simpleQueue.push(user1);
        simpleQueue.push(user2);
        simpleQueue.push(user3);

        User result = (User) simpleQueue.poll();

        User expected = user1;

        assertThat(result, is(expected));

    }

}
