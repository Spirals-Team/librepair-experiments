package ru.job4j.loop;
/**
 * Board.
 * @author Hincu Andrei (andreih1981@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Paint {
    /**
     * Метод создает пирамиду высотой h из ^ и пробелов.
     * @param h - высота пирамиды.
     * @return пирамида заданной высоты.
     */
    public String piramid(int h) {
        StringBuilder builder = new StringBuilder();
        int osnova = h * 2;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < osnova; j++) {
                if (j < h + i && j > h - 2 - i) {
                    builder.append("^");
                } else if (j < osnova - 1) {
                    builder.append(" ");
                }
            }
            if (i < h - 1) {
                builder.append(System.getProperty("line.separator"));
            }
        }
        return builder.toString();
    }
}
