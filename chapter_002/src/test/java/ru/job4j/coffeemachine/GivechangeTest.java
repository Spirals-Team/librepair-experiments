package ru.job4j.coffeemachine;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GivechangeTest {

   @Test
   public void whenPayByCoffeeThenGetChange() {
       Givechange givechange = new Givechange(4800, 35);
       int[] result = givechange.getChangeByCoffee();
       int[] expectedResult = new int[]{1000, 1000, 1000, 1000, 500, 100, 100, 50, 10, 5, 0, 0, 0, 0, 0};
       assertThat(result, is(expectedResult));
   }
}