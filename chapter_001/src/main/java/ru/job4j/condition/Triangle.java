package ru.job4j.condition;
/**
 * Triangle.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Triangle {
    /** point a.*/
    private Point a;
    /** point b.*/
    private Point b;
    /** point c.*/
    private Point c;

    /**
     * Constructor.
     * @param a - point a
     * @param b - point b
     * @param c - point c
     */
    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    /**
     * distance. √(xb - xa)^2 + (yb - ya)^2
     * @param left - left point
     * @param right - right point
     * @return distance between left & right points
     */
    public double distance(Point left, Point right) {
        double dx = Math.pow((left.getX() - right.getX()), 2);
        double dy = Math.pow((left.getY() - right.getY()), 2);
        double dist = Math.sqrt(dx + dy);
        return dist;
    }
    /**
     * period. perimeter of triangle (ab + ac + bc) / 2
     * @param ab - distance between a b
     * @param ac - distance between a c
     * @param bc - distance between b c
     * @return perimeter
     */
    public double period(double ab, double ac, double bc) {
        return (ab + ac + bc) / 2;
    }
    /**
     * exist. existence of triangle
     * @param ab - distance between a b
     * @param ac - distance between a c
     * @param bc - distance between b c
     * @return true if the triangle exists or false
     */
    private boolean exist(double ab, double ac, double bc) {
        boolean notZero = (ab > 0) && (ac > 0) && (bc > 0);
        if ((ab + ac > bc) && (ab + bc > ac) && (ac + bc > ab) && notZero) {
            return true;
        }
        return false;
    }
    /**
     * area. area of a triangle √ p *(p - ab) * (p - ac) * (p - bc)
     * @return area if the triangle exists or -1
     */
    public double area() {
        double result = -1;
        double ab = this.distance(this.a, this.b);
        double ac = this.distance(this.a, this.c);
        double bc = this.distance(this.b, this.c);
        double perimeter = this.period(ab, ac, bc);
        if (this.exist(ab, ac, bc)) {
            result = Math.sqrt(perimeter * (perimeter - ab) * (perimeter - ac) * (perimeter - bc));
        }
        return result;
    }
}
