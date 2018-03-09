package aserdyuchenko;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест для класса Point.
 *
 * whenCalculateDistanceBetweenTwoPoints()
 * Когда необходимо найти длинну между двумя точками.
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 28.08.2016
 */
public class PointTest {
/**
 * whenCalculateDistanceBetweenTwoPoints().
 */
    @Test
    public void whenCalculateDistanceBetweenTwoPoints() {
        Point first = new Point(0, 0);
        Point second = new Point(2, 0);
        double result = first.distanceTo(second);
        assertThat(result, is(2d));
    }
}
