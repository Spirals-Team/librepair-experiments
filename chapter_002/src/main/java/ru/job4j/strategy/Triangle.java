package ru.job4j.strategy;

/**
 * Triangle.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 07.09.17;
 * @version $Id$
 * @since 0.1
 */
public class Triangle implements Shape {
    /**
     * Рисуем треугольник.
     * @return треугольник.
     */
    @Override
    public String pic() {
        StringBuilder sb = new StringBuilder();
        sb.append("*").append(System.getProperty("line.separator"));
        sb.append("* * *").append(System.getProperty("line.separator"));
        sb.append("* * * *").append(System.getProperty("line.separator"));
        return sb.toString();
    }
}
