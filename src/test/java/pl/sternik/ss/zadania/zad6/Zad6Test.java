package pl.sternik.ss.zadania.zad6;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class Zad6Test {


    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionForFindMin(){
        assertThat(Zad6.findMin(new int[][]{})).isEqualTo(2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionForFindMax(){
        assertThat(Zad6.findMax(new int[][]{})).isEqualTo(2);
    }

    @Test
    public void shouldSayThat1IsMinNumber() {
        assertThat(Zad6.findMin(new int[][]{{3, 8, 16}, {1, 22, 28, 24}, {3}, {41, 42}})).isEqualTo(1);
    }

    @Test
    public void shouldSayThat42IsMaxNumber() {
        assertThat(Zad6.findMax(new int[][]{{3, 8, 16}, {1, 22, 28, 24}, {3}, {41, 42}})).isEqualTo(42);
    }


}