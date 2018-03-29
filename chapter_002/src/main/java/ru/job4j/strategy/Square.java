package ru.job4j.strategy;

public class Square implements Shape {

    public String draw() {
        StringBuilder builder = new StringBuilder();
        builder.append("++++\n");
        builder.append("+  +\n");
        builder.append("+  +\n");
        builder.append("++++\n");
        return builder.toString();
    }
}
