package ru.job4j.loop;

public class Board {
    public String paint(int width, int height) {
        StringBuilder builder = new StringBuilder();
        int flag = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (j % 2 == flag)
                    builder.append("x");
                else
                    builder.append(" ");
            }
            builder.append("\\n");
            if (flag == 0)
                flag = 1;
            else
                flag = 0;
        }
        String result = builder.toString();
        return result;
    }
}
