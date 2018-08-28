package ru.job4j.loop;

/**
 * Class Board.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 13.09.2017
 */
public class Board {
    /**
     * Метод paint рисует доску.
     * @param width ширина доски
     * @param height высота доски
     * @return вернем StringBuilder в виде строки.
     */
    public String paint(int width, int height) {
        StringBuilder board = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((i + j) % 2 == 0) {
                    board.append("x");
                } else {
                    board.append(" ");
                }
            }
            board.append(System.getProperty("line.separator"));
        }
        return board.toString();
    }
}
