package aserdyuchenko;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест для класса MaxLenght.
 * 1. whenAllPointsInOnePointThenAreaZero()
 * Невозможно построить треугольник, так как все точки находятся в одной точке и площадь равна нулю.
 *
 * 2. whenPointsLieOnLineThenAreaZero()
 * Невозможно построить треугольник, так как все точки на одной линии и площадь равна нулю.
 *
 * 3. whenDistanceBCisMax()
 * Максимальная сторона BC
 *
 * 4. whenDistanceACisMax()
 * Максимальная сторона AC
 *
 * 5. whenDistanceABisMax()
 * МАксимальная сторона AB
 *
 * @author Anton Serdyuchenko
 * @since 02.09.2016
 * @version 1.0
 */
public class MaxLenghtTest {
/**
 * @param NUMBERONE - NUMBERONE.
 */
public static final int NUMBERONE = 4;
/**
 * @param NUMBERTWO - NUMBERTWO.
 */
public static final int NUMBERTWO = 5;
/**
 * whenAllPointsInOnePointThenAreaZero().
 */
    @Test
    public void whenAllPointsInOnePointThenAreaZero() {
        Point a = new Point(0, 0);
        Point b = new Point(0, 0);
        Point c = new Point(0, 0);
        MaxLenght maxLenght = new MaxLenght(a, b, c);
        String result = maxLenght.max();
        assertThat(result, is("Невозможно построить треугольник!"));
    }
/**
 * whenPointsLieOnLineThenAreaZero().
 */
    @Test
    public void whenPointsLieOnLineThenAreaZero() {
        Point a = new Point(0, 0);
        Point b = new Point(2, 0);
        Point c = new Point(NUMBERONE, 0);
        MaxLenght maxLenght = new MaxLenght(a, b, c);
        String maximum = maxLenght.max();
        assertThat(maximum, is("Невозможно построить треугольник!"));
    }
/**
 * whenDistanceBCisMax().
 */
    @Test
    public void whenDistanceBCisMax() {
        Point a = new Point(0, 0);
        Point b = new Point(2, 0);
        Point c = new Point(0, NUMBERTWO);
        MaxLenght maxLenght = new MaxLenght(a, b, c);
        String maximum = maxLenght.max();
        assertThat(maximum, is("Distance BC is maximum"));
    }
/**
 * whenDistanceACisMax().
 */
    @Test
    public void whenDistanceACisMax() {
        Point a = new Point(0, 0);
        Point b = new Point(2, 0);
        Point c = new Point(2, NUMBERTWO);
        MaxLenght maxLenght = new MaxLenght(a, b, c);
        String maximum = maxLenght.max();
        assertThat(maximum, is("Distance AC is maximum"));
    }
/**
 * whenDistanceABisMax().
 */
    @Test
    public void whenDistanceABisMax() {
        Point a = new Point(0, 0);
        Point b = new Point(NUMBERTWO, 0);
        Point c = new Point(0, 2);
        MaxLenght maxLenght = new MaxLenght(a, b, c);
        String maximum = maxLenght.max();
        assertThat(maximum, is("Distance AB is maximum"));
    }
}