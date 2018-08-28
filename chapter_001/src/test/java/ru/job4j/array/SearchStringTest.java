package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test class SearchStringTest.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 20.09.2017
 */
public class SearchStringTest {
    /**
     * Method giveTwoWordsLookingForAMatch.
     */
    @Test
    public void giveTwoWordsLookingForAMatch() {
        SearchString searchString = new SearchString();
        assertThat(searchString.contains("Привет", "иве"), is(true));
    }
    /**
     * Method giveTwoWordsLookingForAMatch2.
     */
    @Test
    public void giveTwoWordsLookingForAMatch2() {
        SearchString searchString = new SearchString();
        assertThat(searchString.contains("водогрязеторфопарафинолечение", "парафин"), is(true));
    }
}
