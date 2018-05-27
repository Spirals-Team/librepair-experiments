package ru.job4j.loop;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Board {

    /**
     * Метод рисует шахматную доску из символов x и пробелов по заданным размерам.
     * @param width - ширина шахматной доски.
     * @param height - высота шахматной доски.
     * @return - строку содержащую шахматную доску.
     */
    public String paint(int width, int height) {
        StringBuilder screen = new StringBuilder();
        String ln = System.lineSeparator();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // условие проверки, что писать пробел или X
                // Если координата четная, ставим Х иначе пробел.
                if ((i + j) % 2 == 0) {
                    screen.append("X");
                } else {
                    screen.append(" ");
                }
            }
            // добавляем перевод на новую строку.
            final String line = System.getProperty("line.separator");
            screen.append(line);
        }
        return screen.toString();
    };
}
