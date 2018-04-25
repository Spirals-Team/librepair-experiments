package aserdyuchenko;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**2.3. Создать программу калькулятор.
 * Тест для класса Calculator
 *
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 28.08.2016
 */
public class CalculatorTest {
/**
 * @param NUMBER - NUMBER.
 */
public static final int NUMBER = 3;
/**
 * @param NUMBERDIV - NUMBERDIV.
 */
public static final int NUMBERDIV = 4;
/**
 * whenAddTwoNumbers().
 */
    @Test
    public void whenAddTwoNumbers() {
        Calculator calculate = new Calculator();
        calculate.add(1, 1);
        double result = calculate.getResult();
        assertThat(result, is(2d));
    }
/**
 * whenSubstructTwoNumbers().
 */
    @Test
    public void whenSubstructTwoNumbers() {
        Calculator calculate = new Calculator();
        calculate.substruct(NUMBER, 1);
        double result = calculate.getResult();
        assertThat(result, is(2d));
    }
/**
 * whenDivTwoNumbers().
 */
    @Test
    public void whenDivTwoNumbers() {
        Calculator calculate = new Calculator();
        calculate.div(NUMBERDIV, 2);
        double result = calculate.getResult();
        assertThat(result, is(2d));
    }
/**
 * whenMultipleTwoNumbers().
 */
    @Test
    public void whenMultipleTwoNumbers() {
        Calculator calculate = new Calculator();
        calculate.multiple(2, 1);
        double result = calculate.getResult();
        assertThat(result, is(2d));
    }
}
