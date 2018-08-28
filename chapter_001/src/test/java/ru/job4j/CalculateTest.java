/**
 * Package for calculate test task.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 07.09.2017
 */
package ru.job4j;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test class Calculate.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 07.09.2017
 */

public class CalculateTest {
    /**
     * Method whenTakeNameThenTreeEchoPlusName.
     */
    @Test
    public void whenTakeNameThenTreeEchoPlusName() {
        String input = "Petr Arsentev";
        String expect = "Echo, echo, echo : Petr Arsentev";
        Calculate calc = new Calculate();
        String result = calc.echo(input);
        assertThat(result, is(expect));
}
}