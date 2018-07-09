
package coaching.tuples;

/**
 * Twin Tuple Class.
 *
 * @param <L>
 *            the generic type
 * @param <R>
 *            the generic type
 */
public class Tuple<L, R> implements TupleInterface<L, R> {

    /** The left hand side of Tuple. */
    private L left;

    /** The right hand side of Tuple. */
    private R right;

    /**
     * Instantiates a new tuple.
     */
    public Tuple() {
        super();
        this.left = null;
        this.right = null;
    }

    /**
     * Instantiates a new tuple.
     *
     * @param left
     *            the generic type L
     * @param right
     *            the generic type R
     */
    public Tuple(final L left, final R right) {
        super();
        setLeft(left);
        setRight(right);
    }

    /**
     * a factory method to create a instance of tuple from values.
     *
     * @param <L> the generic type
     * @param <R> the generic type
     * @param left the generic type L
     * @param right the generic type R
     * @return the tuple interface
     */
    public static <L, R> TupleInterface<L, R> pair(final L left, final R right) {
        return new Tuple<>(left, right);
    }

    /*
     * (non-Javadoc)
     *
     * @see idioms.tuples.TupleInterface#setLeft(L)
     */
    @Override
    public void setLeft(final L left) {
        this.left = left;
    }

    /*
     * (non-Javadoc)
     *
     * @see idioms.tuples.TupleInterface#setRight(R)
     */
    @Override
    public void setRight(final R right) {
        this.right = right;
    }

    /*
     * (non-Javadoc)
     *
     * @see idioms.tuples.TupleInterface#getLeft()
     */
    @Override
    public L getLeft() {
        return this.left;
    }

    /*
     * (non-Javadoc)
     *
     * @see idioms.tuples.TupleInterface#getRight()
     */
    @Override
    public R getRight() {
        return this.right;
    }

    /**
     * Checks if is equal to.
     *
     * @param tuple
     *            the tuple
     * @return true, if is equal to
     */
    public boolean isEqualTo(final TupleInterface<L, R> tuple) {
        return this.left.equals(tuple.getLeft()) && this.right.equals(tuple.getRight());
    }

    /*
     * (non-Javadoc)
     *
     * @see idioms.tuples.TupleInterface#copy()
     */
    @Override
    public TupleInterface<L, R> copy() {
        return new Tuple<>(getLeft(), getRight());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Tuple [left=%s, right=%s]", this.left, this.right);
    }

}
