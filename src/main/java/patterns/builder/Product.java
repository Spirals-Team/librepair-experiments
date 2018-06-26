
package patterns.builder;

/**
 * Product Class.
 */
public class Product {

    /** The part one. */
    private Part partOne = null;

    /** The part two. */
    private Part partTwo = null;

    /**
     * Instantiates a new product.
     *
     * @param partOne
     *            the part one
     * @param partTwo
     *            the part two
     */
    public Product(final Part partOne, final Part partTwo) {
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
        return String.format("Product [partOne=%s, partTwo=%s]", this.partOne, this.partTwo);
    }

}
