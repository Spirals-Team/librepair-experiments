package ru.job4j.strategytask;

public class ShapeSquare implements Shape {
    @Override
    public String pic() {
        return draw();
    }

    public String draw() {
        StringBuilder pic = new StringBuilder();
        pic.append("++++");
        pic.append("+     +");
        pic.append("+     +");
        pic.append("++++");
        return pic.toString();
    }
}
