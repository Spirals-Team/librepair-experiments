package ru.job4j.loop;
/**
 * to build a chessboard in pseudo-graphics.
 *
 * @author Afonin Sergey (afonin1c@mail.ru)
 * @version 01
 * @since 10.10.2017
 */
public class Board {

    /**
     * Method paint.
     * @param width chessboard width.
     * @param height chessboard height.
     * @return arithmetic sums of even numbers.
     */
    public String paint(int width, int height) {
        //Для сложения строк использовать класс StringBuilder - и методы append(String), String toString().

        StringBuilder line = new StringBuilder();

        boolean itisx = true;

        for (int k = 1; k <= height; k++) {
            for (int i = 1; i <= width; i++) {
                if (itisx) {
                    line.append("x");
                } else {
                    line.append(" ");
                }
                if (i == width) {
                    line.append("\r\n");
                }
                itisx = !itisx;
            }
        }
        return line.toString();
    }
}

