package ru.job4j.end;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class FindSubWordTest {
    @Test
    public void whenWordHaveSubWordThenTrue() {
        FindSubWord subWord = new FindSubWord();
        assertThat(subWord.contains("village", "age"), is(true));
    }

    @Test
    public void whenWordDoesNotHaveSubWordThenFalse() {
        FindSubWord subWord = new FindSubWord();
        assertThat(subWord.contains("village", "vid"), is(false));
    }

    @Test
    public void whenWordHasOnlySubLetterThenTrue() {
        FindSubWord subWord = new FindSubWord();
        assertThat(subWord.contains("village", "a"), is(true));
    }
}
