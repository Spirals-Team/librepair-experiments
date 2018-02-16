package pl.sternik.ss.zadania.zad11;


import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Zad11Test {

    @Test
    public void checkIfArraysAreCalculatedProperly() {
        String actual = Zad11.printOrderState(Zad11.OrderState.REALIZOWANE);
        String expected = "PILNE!";
        assertThat(actual).isEqualTo(expected);
    }

}