package aserdyuchenko;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест для рассчета формулы квадратного уровнения.
 * Подробное описание поиска площади, расписано в классе Square.
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 28.08.2016
 */
public class SquareTest {
/**
 * @param NUMBER - NUMBER.
 */
public static final float NUMBER = 14f;
/**
 * whenLookingValueQuadraticEquation().
 */
    @Test
    public void whenLookingValueQuadraticEquation() {
        Square square = new Square(2f, 2f, 2f);
        float result = square.calculate(2);
        assertThat(result, is(NUMBER));
    }
/**
 * whenDisplayedScreenFunctionValueByStep().
 */
    @Test
    public void whenDisplayedScreenFunctionValueByStep() {
        Square square = new Square(1f, 1f, 1f);
        float start = square.calculate(1);
        float finish = square.calculate(2);
        float step = 1f;
		try {
        	square.show(start, finish, step);
    	} catch (java.io.IOException e) {
       		// Do something here
    	}
		assertThat(step, is(1f));
    }
}
