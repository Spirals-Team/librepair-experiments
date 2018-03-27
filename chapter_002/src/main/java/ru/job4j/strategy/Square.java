package ru.job4j.strategy;

/**
 * Square.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 07.09.17;
 * @version $Id$
 * @since 0.1
 */
public class Square implements Shape {
    /**
     * Рисуем квадрат.
     * @return квадрат.
     */
    @Override
    public String pic() {
        StringBuilder sb = new StringBuilder();
        sb.append("* * *").append(System.getProperty("line.separator"));
        sb.append("* * *").append(System.getProperty("line.separator"));
        sb.append("* * *").append(System.getProperty("line.separator"));
        return sb.toString();
    }
}