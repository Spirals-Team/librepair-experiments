package ru.job4j.loop;

/**
 * Board.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Board {
    /**
     * dark cell.
     */
    private final char darkCell = 'x';
    /**
     * light cell.
     */
    private final char lightCell = ' ';
    /**
     * Draws a chessboard.
     * @param width - width of board
     * @param height - height of board
     * @return string
     */
    public String paint(int width, int height) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char cell = ((i + j) % 2 == 0) ? darkCell : lightCell;
                stringBuilder.append(cell);
            }
            stringBuilder.append(System.getProperty("line.separator"));
        }
        return stringBuilder.toString();
    }
}
