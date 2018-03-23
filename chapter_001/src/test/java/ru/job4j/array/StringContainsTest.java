package ru.job4j.array;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
/**
 * String contains a substring.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class StringContainsTest {
    /**
     * String contains a substring.
     */
    @Test
    public void whenStringContainsSubstringThenTrue() {
        StringContains stringContains = new StringContains();
        boolean result = stringContains.contains("Hello", "Hel");
        assertThat(result, is(true));
    }
    /**
     * String does not contain a substring.
     */
    @Test
    public void whenStringNotContainsSubstringThenFalse() {
        StringContains stringContains = new StringContains();
        boolean result = stringContains.contains("Hello", "ole");
        assertThat(result, is(false));
    }
}
