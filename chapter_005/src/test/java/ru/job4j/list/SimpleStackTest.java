package ru.job4j.list;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

import ru.job4j.generic.*;

public class SimpleStackTest {

    @Test
    public void whenPushTwoObjectsToSimpleStackThenOneCanPollSecondElement() {

        SimpleStack simpleStack = new SimpleStack();

        User user1 = new User("1234");
        User user2 = new User("2222");

        simpleStack.push(user1);
        simpleStack.push(user2);

        User result = (User) simpleStack.poll();

        User expected = user2;

        assertThat(result, is(expected));

    }

}
