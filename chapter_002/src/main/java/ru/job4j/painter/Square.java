package ru.job4j.painter;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Square implements Shape {
    @Override
    public String draw() {
        StringBuilder pic = new StringBuilder();
        pic.append("+ + + +\n");
        pic.append("+ + + +\n");
        pic.append("+ + + +\n");
        pic.append("+ + + +");
        return pic.toString();
    }
}
