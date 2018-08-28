package ru.job4j.condition;

/**
 * Class Triangle.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 11.09.2017
 */
public class Triangle {
    /**
     * Точка А.
     */
    private Point a;
    /**
     * Точка B.
     */
    private Point b;
    /**
     * Точка С.
     */
    private Point c;

    /**
     * Конструктор треугольника.
     *
     * @param a - точка А
     * @param b - точка В
     * @param c - точка С
     */
    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Метод должен вычислять расстояние между точками left и right.
     * <p>
     * Для вычисления расстояния использовать формулу.
     * √(xb - xa)^2 + (yb - ya)^2
     * <p>
     * где √ - корень квадратный, для извлечения корня использовать метод Math.sqrt().
     * <p>
     * ^ - степень.
     *
     * @param left  Точка слева
     * @param right Точка с права.
     * @return расстояние между точками left и right.
     */
    public double distance(Point left, Point right) {
        double y = left.getY() - right.getY();
        return Math.sqrt(Math.pow(left.getX() - right.getX(), 2)
                + Math.pow(left.getY() - right.getY(), 2));
    }

    /**
     * Метод вычисления периметра по длинам сторон.
     * <p>
     * Формула.
     * <p>
     * (ab + ac + bc) / 2
     *
     * @param ab расстояние между точками a b
     * @param ac расстояние между точками a c
     * @param bc расстояние между точками b c
     * @return Перимент.
     */
    public double period(double ab, double ac, double bc) {
        return ((ab + ac + bc) / 2);
    }

    /**
     * Метод должен вычислить прощадь треугольканива.
     * <p>
     * Формула.
     * <p>
     * √ p *(p - ab) * (p - ac) * (p - bc)
     * <p>
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

    /**
     * Метод проверяет можно ли построить треугольник с такими длинами сторон.
     * <p>
     * Подумайте какое надо написать условие, чтобы определить можно ли построить треугольник.
     *
     * @param ab Длина от точки a b.
     * @param ac Длина от точки a c.
     * @param bc Длина от точки b c.
     * @return Если можно построить треугольник с такими длинами сторон вернем true.
     */
    private boolean exist(double ab, double ac, double bc) {
        return !(ab + ac <= bc || ab + bc <= ac || bc + ac <= ab);
    }
}
