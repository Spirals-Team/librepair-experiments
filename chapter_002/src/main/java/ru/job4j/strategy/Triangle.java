package ru.job4j.strategy;

public class Triangle implements Shape {

    public String draw() {
        StringBuilder builder = new StringBuilder();
        builder.append("   +   \n");
        builder.append("  + +  \n");
        builder.append(" +   + \n");
        builder.append("+++++++\n");
        return builder.toString();
    }
}
