
package coaching.tuples;

/**
 * Tuple Interface.
 *
 * @param <L>
 *            the l generic type L.
 * @param <R>
 *            the r generic type R.
 */
public interface TupleInterface<L, R> {

    /**
     * left.
     *
     * new left
     *
     * @param left
     *            the new left
     */
    void setLeft(final L left);

    /**
     * right.
     *
     * @param right
     *            the new right
     */
    void setRight(final R right);

    /**
     * left.
     *
     * @return the left
     */
    L getLeft();

    /**
     * right.
     *
     * @return the right
     */
    R getRight();

    /**
     * Return a shallow copy of this.
     *
     * @return the tuple interface
     */
    TupleInterface<L, R> copy();

}
