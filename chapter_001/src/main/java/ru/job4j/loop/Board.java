package ru.job4j.loop;

public class Board {
    /**
     * Метод paint строит шахматную доску в псевдографике.
     *
     * @param width  - это ширина доски.
     * @param height - это высота доски.
     * @return - возвращает все добавленные символы и строки.
     * @author Alexandar Vysotskiy
     * @version 1.0
     */
    public String paint(int width, int height) {
        StringBuilder screen = new StringBuilder();
        String ln = System.lineSeparator();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((i + j) % 2 == 0) {
                    screen.append("X");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(ln);
        }
        return screen.toString();
    }
}

