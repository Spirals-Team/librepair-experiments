package ru.job4j.words;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class wordsTest {

    @Test
    /**
     * Тестируем различные слова
     */
    public void testCompareWords() {
        assertThat(words.compareTwoWords("корова", "ваокор"), is(true));
        assertThat(words.compareTwoWords("абракадабр", "адабрабрак"), is(true));
        assertThat(words.compareTwoWords("корова", "ваокора"), is(false));
        assertThat(words.compareTwoWords("корова", "ваовор"), is(false));
    }
}