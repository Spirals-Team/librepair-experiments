package ru.job4j.loop;

/**
 * Paint.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Paint {
    /**
     * symbol = '^'.
     */
    private final char symbol = '^';
    /**
     * symbol = ' '.
     */
    private final char empty = ' ';
    /**
     * Draw pyramid.
     * @param h - height of pyramid
     * @return string
     */
    public String pyramid(int h) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= h; i++) {
            for (int j = 1; j <= h * 2 - 1; j++) {
                if (j < h) {
                    stringBuilder.append((j <= h - i) ? empty : symbol);
                }
                if (j >= h) {
                    stringBuilder.append((j < h + i) ? symbol : empty);
                }
            }
            if (i != h) {
                stringBuilder.append(System.getProperty("line.separator"));
            }
        }
        return stringBuilder.toString();
    }
}
