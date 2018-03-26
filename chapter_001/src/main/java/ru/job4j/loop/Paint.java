package ru.job4j.loop;

public class Paint {
    public String piramid(int h) {
        StringBuilder builder = new StringBuilder();
        int width = 1;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < width; j++) {
                builder.append("^");
            }
            width += 2;
            builder.append("\\n");
        }
        String result = builder.toString();
        return result;
    }
}