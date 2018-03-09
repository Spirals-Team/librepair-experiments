package aserdyuchenko;

/**
 * Класс Point вычисляет расстояние между двумя точками в системе координат.
 *
 * Расстояние (distance) вычисляется следующим образом:
 * 1. Координата x первой точки минус координата x второй точки.
 * 2. Координата у первой точки минус координата у второй точки.
 * 3. Сумма квадратов, первых двух вычислений.
 * 4. Корень получивщегося значения в пункте 3.
 *
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 31.08.2016
 */
public class Point {
/**
 * @param x - x.
 */
    private double x;
/**
 * @param y - y.
 */
    private double y;
/**
 * @param xPoint - xPoint.
 * @param yPoint - yPoint.
 */
    public Point(double xPoint, double yPoint) {
        this.x = xPoint;
        this.y = yPoint;
    }
/**
 * getX().
 * @return this.x - this.x.
 */
	public double getX() {
		return this.x;
	}
/**
 * getY().
 * @return this.y - this.y.
 */
	public double getY() {
		return this.y;
	}
/**
 * @param point - point.
 * @return Math.sqrt(Math.pow((point.x - this.x), 2) + Math.pow((point.y - this.y), 2)) - distanceTo.
 */
    public double distanceTo(Point point) {
        return Math.sqrt(Math.pow((point.x - this.x), 2) + Math.pow((point.y - this.y), 2));
    }
}
