package ru.job4j.loop;
/**
 * Board.
 * @author Hincu Andrei (andreih1981@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class Board {
    /**
     * Метод возвращает доску заданной высоты и ширины.
     * @param width -высота доски.
     * @param height - ширина доски.
     * @return String paint.
     */
    public String paint(int width, int height) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    sb.append("x");
                }
                if (i % 2 == 0 && j % 2 != 0) {
                    sb.append(" ");
                }
                if (i % 2 != 0 && j % 2 == 0) {
                    sb.append(" ");
                }
                if (i % 2 != 0 && j % 2 != 0) {
                    sb.append("x");
                }
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

}
