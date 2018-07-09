
package patterns.iterator;

/**
 * Iterator Class.
 */
public final class Iterator extends AbstractIterator {

    /**
     * Instantiates a new iterator.
     *
     * @param abstractAggregate
     *            the abstract aggregate
     */
    public Iterator(final AggregateInterface abstractAggregate) {
        super(abstractAggregate);
    }

}
