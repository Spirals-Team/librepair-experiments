package ru.job4j.loop;

import java.util.function.BiPredicate;

public class Paint {

    private String loopBy(int height, int weight, BiPredicate<Integer, Integer> predict) {
        StringBuilder screen = new StringBuilder();
        for (int row = 0; row != height; row++) {
            for (int column = 0; column != weight; column++) {
                if (predict.test(row, column)) {
                    screen.append("^");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(System.lineSeparator());
        }
        return screen.toString();
    }
    public String rightTrl(int height) {

        return this.loopBy(height, height, (row, colum) -> row >= colum);
    }
    public String leftTrl(int height) {

        return this.loopBy(height, height, (row, colum) -> row >= height - colum - 1);
    }
    public String pyramid(int height) {
        int weight = 2 * height - 1;

        return this.loopBy(height, weight, (row, colum) -> row >= height - colum - 1 && row + height - 1 >= colum);
    }

}