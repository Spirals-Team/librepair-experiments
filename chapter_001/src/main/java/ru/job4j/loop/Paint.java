package ru.job4j.loop;
/**
 * Pain a pyramid with height equals h in pseudo-graphics.
 *
 * @author Afonin Sergey (afonin1c@mail.ru)
 * @version 01
 * @since 10.10.2017
 */
public class Paint {

    /**
     * Method pyramid pain a pyramid with height equals h in pseudo-graphics.
     * @param h pyramid height.
     * @return arithmetic sums of even numbers.
     */
    public String piramid(int h) {

        StringBuilder line = new StringBuilder();

        for (int i = 1; i <= h; i++) {

            if (h > 1) {

                for (int j = 1; j <= ((2 * h - 1 - 2 * (i - 1)) - 1) / 2; j++) {
                    line.append(" ");
                }
            }

            for (int k = 1; k <= ((i == 1) ? i : 2 * i - 1); k++) {
                line.append("^");
            }

            if (h > 1) {

                for (int j = 1; j <= ((2 * h - 1 - 2 * (i - 1)) - 1) / 2; j++) {
                    line.append(" ");
                }
            }

            if (i < h) {
                line.append("\r\n");
            }

        }
        return line.toString();
    }

}
