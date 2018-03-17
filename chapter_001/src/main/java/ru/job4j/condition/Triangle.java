package ru.job4j.condition;
/**
 * Triangle.
 * @author Hincu Andrei (andreih1981@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Triangle {
    /**
     * Вершина а.
     */
    private Point a;
    /**
     * Вершина b.
     */
    private Point b;
    /**
     * Вершина c.
     */
    private Point c;

    /**
     * Конструктор класса Triangle.
     * @param a - точка а с координатами (х, у).
     * @param b - точка b с координатами (х, у)
     * @param c - точка c с координатами (х, у).
     */
    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Triangle() {
    }

    /**
     * Метод вычисляет расстояние между точками.
     * формула  √(xb - xa)^2 + (yb - ya)^2.
     * @param left -точка с лева.
     * @param right - точка с права.
     * @return - расстояние между точками.
     */
    public double distance(Point left, Point right) {
        return Math.sqrt(Math.pow((right.getX() - left.getX()), 2) + Math.pow((right.getY() - left.getY()), 2));
    }
    /**
     * Метод вычисления Полупериметра по длинам сторон.
     * Формула.
     * (ab + ac + bc) / 2
     *
     * @param ab расстояние между точками a b
     * @param ac расстояние между точками a c
     * @param bc расстояние между точками b c
     * @return Полупериметр.
     */
    public double period(double ab, double ac, double bc) {
        return (ab + ac + bc) / 2;
    }
    /**
     * Метод проверяет можно ли построить треугольник с такими длинами сторон.
     * @param ab Длина от точки a до b.
     * @param ac Длина от точки a до c.
     * @param bc Длина от точки b до c.
     * @return true or false.
     */
    public boolean exist(double ab, double ac, double bc) {
        return ((ab + ac) > bc) && ((ab + bc) > ac) && ((ac + bc) > ab);
    }
    /**
     * Метод должен вычислить прощадь треугольканива.
     *
     * Формула.
     *
     * √ p *(p - ab) * (p - ac) * (p - bc)
     *
     * где √ - корень квадратный, для извлечения корня использовать метод Math.sqrt().
     *
     * @return Вернуть прощадь, если треугольник существует или -1.
     */
    public double area() {
        double rsl = -1;
        double ab = this.distance(this.a, this.b);
        double ac = this.distance(this.a, this.c);
        double bc = this.distance(this.b, this.c);
        double p = this.period(ab, ac, bc);
        if (this.exist(ab, ac, bc)) {
            rsl = Math.sqrt(p * (p - ab) * (p - ac) * (p - bc));
        }
        return rsl;
    }
}
