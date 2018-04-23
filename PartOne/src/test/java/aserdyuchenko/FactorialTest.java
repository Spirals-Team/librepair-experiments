package aserdyuchenko;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест для вычисления факториала.
 * Подробное описание вычисления факториала, расписано в классе Factorial.
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 28.08.2016
 */
public class FactorialTest {
/**
 * @param NUMBERONE - NUMBERONE.
 */
public static final float NUMBERONE = 5f;
/**
 * @param NUMBERTWO - NUMBERTWO.
 */
public static final float NUMBERTWO = 120f;
/**
 * whenCalculateTheFactorial.
 */
    @Test
    public void whenCalculateTheFactorial() {
        Factorial factorial = new Factorial();
        float result = factorial.calculation(NUMBERONE);
        assertThat(result, is(NUMBERTWO));
    }
}