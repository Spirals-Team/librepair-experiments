package ru.job4j.strategytask;

/**
 * context shape.
 */
public class ContextShape {
    /**
     * shape.
     */
    private Shape shape;

    /**
     *
     * @param shape is shape.
     */
    public ContextShape(Shape shape) {
        this.shape = shape;
    }

    /**
     * execute operation.
     * @return picture.
     */
    public String executeShape() {
        return shape.pic();
    }
}
