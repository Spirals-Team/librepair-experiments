package aserdyuchenko;

/**
 * Класс Triangle рассчитывает площадь треугольника.
 *
 * Площадь тругольника рассчитывается по формуле Герона:
 * площадь равна корню из произведения разностей полупериметра треугольника (semiperimeter)
 * и каждой из его сторон (distanceAB, distanceAC, distanceBC):
 *
 * Возвращает "Невозможно построить треугольник!", если площадь треугольника равно 0.
 *
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 01.09.2016
 */
public class Triangle {
/**
 * @param a - a.
 */
    private Point a;
/**
 * @param b - b.
 */
    private Point b;
/**
 * @param c - c.
 */
    private Point c;
/**
 * @param aPoint - aPoint.
 * @param bPoint - bPoint.
 * @param cPoint - cPoint.
 */
    public Triangle(Point aPoint, Point bPoint, Point cPoint) {
        this.a = aPoint;
        this.b = bPoint;
        this.c = cPoint;
    }
/**
 * area().
 * @return square - square.
 */
    public double area() {
/**
 * @param distanceAB - distanceAB.
 */
        double distanceAB = Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
/**
 * @param distanceAC - distanceAC.
 */
        double distanceAC = Math.sqrt(Math.pow((a.getX() - c.getX()), 2) + Math.pow((a.getY() - c.getY()), 2));
/**
 * @param distanceBC - distanceBC.
 */
        double distanceBC = Math.sqrt(Math.pow((b.getX() - c.getX()), 2) + Math.pow((b.getY() - c.getY()), 2));
/**
 * @param semiperimeter - semiperimeter.
 */
        double semiperimeter = (distanceAB + distanceAC + distanceBC) / 2;
/**
 * @param square - square.
 */
        double square = Math.sqrt((semiperimeter - distanceAB) * (semiperimeter - distanceAC) * (semiperimeter - distanceBC) * semiperimeter);
        if (square == 0) {
		System.out.println("Невозможно построить треугольник!");
	}
        return square;
    }
}
