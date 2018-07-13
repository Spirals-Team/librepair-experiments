package ru.job4j.condition;

public class Triangle {
    private Point a;
    private Point b;
    private Point c;

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Метод должен вычислять расстояние между точками left и right.
     *
     * Для вычисления расстояния использовать формулу.
     * √(xb - xa)^2 + (yb - ya)^2
     *
     * где √ - корень квадратный, для извлечения корня использовать метод Math.sqrt().
     *
     * ^ - степень.
     *
     * @param left Точка слева
     * @param right Точка с права.
     * @return расстояние между точками left и right.
     */
    public double distance(Point left, Point right) {
        return Math.sqrt(Math.pow(left.getX()-right.getX(),2)+Math.pow(left.getY()-right.getY(),2));
    }

    /**
     * Метод вычисления периметра по длинам сторон.
     *
     * Формула.
     *
     * ab + ac + bc
     *
     * @param ab расстояние между точками a b
     * @param ac расстояние между точками a c
     * @param bc расстояние между точками b c
     * @return Периметр
     */
    public double period(double ab, double ac, double bc) {
        return ab+ac+bc;
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
        double p = this.period(ab, ac, bc)/2;
        if (this.exist(ab, ac, bc)) {
            // написать формулу для расчета площади треугольника.
            //rsl = ...
            rsl=Math.sqrt(p*(p-ab)*(p-ac)*(p-bc));
        }
        return rsl;
    }

    /**
     * Метод проверяет можно ли построить треугольник с такими длинами сторон.
     *
     * Каждая сторона треугольника меньше суммы двух других сторон.
     *
     * @param ab Длина от точки a b.
     * @param ac Длина от точки a c.
     * @param bc Длина от точки b c.
     * @return
     */
    public boolean exist(double ab, double ac, double bc) {
        return (ab<ac+bc)& (ac<ab+bc)&(bc<ac+ab) ? true : false;
    }
}