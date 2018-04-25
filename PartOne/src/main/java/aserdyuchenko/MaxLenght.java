package aserdyuchenko;

/**
 * 3.2. Класс определяет максимальную длинну стороны треугольника.
 * Построенного в классе Triangle.
 * Сначала вызов метода area. Мы должны быть уверены, что треугольник можно построить.
 * Потом узнать какая сторона больше. Стороны из треугольника, которого мы построили.
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @version 1.0
 */
public class MaxLenght {
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
     * rotationMatrix(int[][] values) .
     * @param aPoint - aPoint.
     * @param bPoint - bPoint.
     * @param cPoint - cPoint.
     */
    public MaxLenght(Point aPoint, Point bPoint, Point cPoint) {
    /**
     * @param aPoint - aPoint.
     */
        this.a = aPoint;
    /**
     * @param bPoint - bPoint.
     */
        this.b = bPoint;
    /**
     * @param cPoint - cPoint.
     */
        this.c = cPoint;
    }
    /**
     * rotationMatrix(int[][] values).
     * @return maximum - maximum.
     */
    public String max() {
    /**
     * @param maximum - maximum.
     */
        String maximum;
        Triangle triangle = new Triangle(a, b, c);
        double distanceAB = Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
        double distanceAC = Math.sqrt(Math.pow((a.getX() - c.getX()), 2) + Math.pow((a.getY() - c.getY()), 2));
        double distanceBC = Math.sqrt(Math.pow((b.getX() - c.getX()), 2) + Math.pow((b.getY() - c.getY()), 2));
        if (distanceAB == 0) {
		maximum = "Невозможно построить треугольник!";
        } else if (triangle.area() == 0) {
		maximum = "Невозможно построить треугольник!";
        } else if (distanceAB > distanceAC) {
		maximum = "Distance AB is maximum";
        } else if (distanceBC > distanceAC) {
		maximum = "Distance BC is maximum";
        } else {
		maximum = "Distance AC is maximum";
        }
	return maximum;
    }
}
