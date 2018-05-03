package ru.job4j.loop;

/**
 * @author Alexander Kaleganov
 * @version Шахмотная доска v 1.00
 * @since  30.01.2018 15:55
 */
public class Board {
    public String paint(int width, int height) { //width - это ширина доски, height - это высота доски;
        StringBuilder screen = new StringBuilder();
        String ln = System.lineSeparator();
        for (int i = 1; i <= height; i++) {
            for (int j = 1; j <= width; j++) {
                if ((j + i) % 2 == 0) {
                    screen.append("x");
                } else {
                    screen.append(" ");
                }
            } screen.append(ln);
        }
return screen.toString();
    }
}
