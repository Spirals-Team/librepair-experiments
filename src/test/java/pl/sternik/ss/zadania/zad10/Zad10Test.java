package pl.sternik.ss.zadania.zad10;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class Zad10Test {

    @Test
    public void checkIfArraysAreCalculatedProperly() {
        int[][] numbersArray = {{1, 1, 1, 2}, {2, 1, 2, 2}};
        String actual = Zad10.calcArray(numbersArray);
        String expected = "[3, 2, -1, 4]";
        assertThat(actual).isEqualTo(expected);

    }



}