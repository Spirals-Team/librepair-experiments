
package patterns.builder;

/**
 * Product Class.
 */
public class Product {

    /** The part one. */
    private AbstractPart partOne = null;

    /** The part two. */
    private AbstractPart partTwo = null;

    /**
     * Instantiates a new product.
     *
     * @param partOne
     *            the part one
     * @param partTwo
     *            the part two
     */
    public Product(final AbstractPart partOne, final AbstractPart partTwo) {
        super();
        this.partOne = partOne;
        this.partTwo = partTwo;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Product [partOne=%s, partTwo=%s]", partOne, partTwo);
    }

}
