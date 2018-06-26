
package coaching.tuples;

/**
 * Triple, 3 Tuple class.
 *
 * @param <X>
 *            the x element type X
 * @param <Y>
 *            the y element type Y
 * @param <Z>
 *            the z element type Z
 */
public class Triple<X, Y, Z> {

    /** The x. */
    private X x;

    /** The y. */
    private Y y;

    /** The z. */
    private Z z;

    /**
     * Instantiates a new tuple.
     */
    public Triple() {
        super();
    }

    /**
     * Instantiates a new triple.
     *
     * @param x
     *            the generic type X
     * @param y
     *            the generic type Y
     * @param z
     *            the generic type Z
     */
    public Triple(final X x, final Y y, final Z z) {
        super();
        setX(x);
        setY(y);
        setZ(z);
    }

    /**
     * a factory method to create a instance of triple from default.
     *
     * @param <X> the generic type
     * @param <Y> the generic type
     * @param <Z> the generic type
     * @return the triple
     */
    public static <X, Y, Z> Triple<X, Y, Z> create() {
        return new Triple<>();
    }

    /**
     * a factory method to create a instance of triple from values.
     *
     * @param <X> the generic type
     * @param <Y> the generic type
     * @param <Z> the generic type
     * @param x the generic type X
     * @param y the generic type Y
     * @param z the generic type Z
     * @return the triple
     */
    public static <X, Y, Z> Triple<X, Y, Z> create(final X x, final Y y, final Z z) {
        return new Triple<>(x, y, z);
    }

    /**
     * Return a shallow copy of this triple.
     *
     * @return the triple
     */
    public Triple<X, Y, Z> copy() {
        return new Triple<>(getX(), getY(), getZ());
    }

    /**
     * set the value of x.
     *
     * @param x
     *            the X
     * @return this for fluent interface.
     */
    public Triple<X, Y, Z> setX(final X x) {
        this.x = x;
        return this;
    }

    /**
     * set the value of y.
     *
     * @param y
     *            the Y
     * @return this for fluent interface.
     */
    public Triple<X, Y, Z> setY(final Y y) {
        this.y = y;
        return this;
    }

    /**
     * set the value of z.
     *
     * @param z
     *            the Z
     * @return this for fluent interface.
     */
    public Triple<X, Y, Z> setZ(final Z z) {
        this.z = z;
        return this;
    }

    /**
     * get the value of x.
     *
     * @return the x
     */
    public X getX() {
        return this.x;
    }

    /**
     * get the value of y.
     *
     * @return the y
     */
    public Y getY() {
        return this.y;
    }

    /**
     * get the value of z.
     *
     * @return the z
     */
    public Z getZ() {
        return this.z;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Triple [x=%s, y=%s, z=%s]", this.x, this.y, this.z);
    }

}
