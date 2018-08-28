package ru.job4j.loop;

/**
 * Class paint.
 */
public class Paint {
    public String piramid(int h) {
        int height = (h + h) - 1;
        int pr = height / 2;
        int st = 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= h; i++) {
            for (int j = 0; j < pr; j++) {
                builder.append(" ");
            }
            for (int j = 0; j < st; j++) {
                builder.append("^");
            }
            st = st + 2;
            for (int j = 0; j < pr; j++) {
                builder.append(" ");
            }
            pr--;
            builder.append(System.getProperty("line.separator"));
        }
        return builder.toString();
    }
}
