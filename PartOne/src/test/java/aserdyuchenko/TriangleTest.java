package aserdyuchenko;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест для рассчета площади треугольника.
 * Подробное описание поиска площади, расписано в классе Triangle.
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 28.08.2016
 */
public class TriangleTest {
/**
 * @param NUMBERONE - NUMBERONE.
 */
public static final int NUMBERONE = -2;
/**
 * @param NUMBERTWO - NUMBERTWO.
 */
public static final int NUMBERTWO = 20;
/**
 * @param NUMBERTREE - NUMBERTREE.
 */
public static final double NUMBERTREE = 20d;
/**
 * @param NUMBERFOUR - NUMBERFOUR.
 */
public static final int NUMBERFOUR = 4;
/**
 * whenThePointsOfTheTriangleDoesNotLieOnTheSameLine.
 */
    @Test
    public void whenThePointsOfTheTriangleDoesNotLieOnTheSameLine() {
        Point a = new Point(0, 0);
        Point b = new Point(NUMBERONE, 0);
        Point c = new Point(0, NUMBERTWO);
        Triangle triangle = new Triangle(a, b, c);
        double square = triangle.area();
        assertThat(square, is(NUMBERTREE));
    }
/**
 * whenPointsLieOnLineThenAreaZero.
 */
    @Test
    public void whenPointsLieOnLineThenAreaZero() {
        Point a = new Point(0, 0);
        Point b = new Point(2, 0);
        Point c = new Point(NUMBERFOUR, 0);
        Triangle triangle = new Triangle(a, b, c);
        double square = triangle.area();
        assertThat(square, is(0.0));
    }
/**
 * whenAllPointsInOnePointThenAreaZero.
 */
    @Test
    public void whenAllPointsInOnePointThenAreaZero() {
        Point a = new Point(0, 0);
        Point b = new Point(0, 0);
        Point c = new Point(0, 0);
        Triangle triangle = new Triangle(a, b, c);
        double square = triangle.area();
        assertThat(square, is(0.0));
    }
}