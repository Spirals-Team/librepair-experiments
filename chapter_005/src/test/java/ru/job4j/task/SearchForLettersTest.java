package ru.job4j.task;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SearchForLettersTest {

    @Test
    public void whenAllLettersAreThereButTheNumberOfLettersIsLess() {
        SearchForLetters searchForLetters = new SearchForLetters();
        boolean result = searchForLetters.allLettersAreInTheRightAmount("mama", "mather");
        boolean expected = false;
        assertThat(result, is(expected));
    }

    @Test
    public void whenAllLettersAreInTheRightAmount() {
        SearchForLetters searchForLetters = new SearchForLetters();
        boolean result = searchForLetters.allLettersAreInTheRightAmount("mtr", "mather");
        boolean expected = true;
        assertThat(result, is(expected));
    }

    @Test
    public void whenAllLettersAreThereButTheNumberOfLettersIsLessFastHashMapAlgorithm() {
        SearchForLetters searchForLetters = new SearchForLetters();
        boolean result = searchForLetters.fastHashMapAlgorithmAllLettersAreInTheRightAmount("mama", "mather");
        boolean expected = false;
        assertThat(result, is(expected));
    }

    @Test
    public void whenAllLettersAreInTheRightAmountFastHashMapAlgorithm() {
        SearchForLetters searchForLetters = new SearchForLetters();
        boolean result = searchForLetters.fastHashMapAlgorithmAllLettersAreInTheRightAmount("mtr", "mather");
        boolean expected = true;
        assertThat(result, is(expected));
    }


}
