package ru.job4j.loop;

public class Board {

    public  String paint(int weidth, int hight) {
    StringBuilder screen = new StringBuilder();
        final String line = System.getProperty("line.separator");
    for (int in = 1;  in <= hight; in++) {
        for (int out = 1; out <= weidth; out++) {
            int sum = in + out;
            if (sum % 2 == 0) {
                screen.append("X");
            } else {
                screen.append(" ");
            }
        }
        screen.append(line);
    }
        return screen.toString();
    }
}
