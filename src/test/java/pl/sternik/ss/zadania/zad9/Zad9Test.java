package pl.sternik.ss.zadania.zad9;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Zad9Test {

    @Test
    public void checkThatRowsAreSwappedProperly() {
        Integer[][] actual = {{32,3},{32,3}};
        Zad9.swapRows(new Integer[][]{{1,2},{3,4}},1,2);
        Integer[][] expected = {{3,4},{1,2}};
        assertThat(actual).isEqualTo(expected);
    }


}