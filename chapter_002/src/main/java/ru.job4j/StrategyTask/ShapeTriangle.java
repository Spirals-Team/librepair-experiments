package ru.job4j.strategytask;

/**
 * triangle.
 */
public class ShapeTriangle implements Shape {
    /**
     * draw shape.
     * @return
     */
    @Override
    public String pic() {
        return draw();
    }

    /**
     *
     * @return picture in string.
     */
    public String draw() {
        StringBuilder pic = new StringBuilder();
        pic.append("   +   ");
        pic.append("  + + ");
        pic.append(" +   + ");
        pic.append("+++++++");
        return pic.toString();
    }
}
