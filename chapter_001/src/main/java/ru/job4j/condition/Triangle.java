package ru.job4j.condition;

/**
 * author Daniil Emelyanov
 * @version $id$
 * @since  12/03/2018
 */
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
     * Метод вычисления полупериметра по длинам сторон.
     * <p>
     * (ab+ac+bc)/2
     *
     * @param ab расстояние между точками а и б
     * @param ac расстояние меду точками а и с
     * @param bc расстояние между двумя точками b и c
     * @return Периметр
     */
    public double period(double ab, double ac, double bc) {
        return (ab + ac + bc) / 2;
    }
    public double area() {
        double result = -1;
        double ab = this.a.distanceTo(this.b);
        double ac = this.a.distanceTo(this.c);
        double bc = this.b.distanceTo(this.c);
        double p = this.period(ab, ac, bc);
        if (this.exist(ab, ac, bc)) {
            result = Math.sqrt(this.period(ab, ac, bc) * (this.period(ab, ac, bc) - ab) * (this.period(ab, ac, bc) - ac) * (this.period(ab, ac, bc) - bc));
        }
        return result;
    }
    private boolean exist(double ab, double ac, double bc) {

        return  (ab < ac + bc && ab < bc + ac &&  ac < bc + ab);
    }
    }