package ru.job4j.words;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class HashWordsTest {
    @Test
    /**
     * Тестируем различные слова
     */
    public void testCompareWords() {
        assertThat(HashWords.compareTwoWords("корова", "ваокор"), is(true));
        assertThat(HashWords.compareTwoWords("абракадабр", "адабрабрак"), is(true));
        assertThat(HashWords.compareTwoWords("корова", "ваокора"), is(false));
        assertThat(HashWords.compareTwoWords("корова", "ваовор"), is(false));
    }
}