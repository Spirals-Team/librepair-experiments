
package patterns.filter;

/**
 * AbstractFilter Class.
 */
public abstract class AbstractFilter implements FilterInterface {

    /** The next filter. */
    private FilterInterface nextFilter = null;

    /**
     * Instantiates a new abstract filter.
     */
    public AbstractFilter() {
        super();
    }

    /**
     * Instantiates a new abstract filter.
     *
     * @param filter
     *            the filter
     */
    public AbstractFilter(final FilterInterface filter) {
        super();
        addNextFilter(filter);
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.filter.FilterInterface#addNextFilter(patterns.filter.
     * FilterInterface)
     */
    @Override
    public FilterInterface addNextFilter(final FilterInterface newFilter) {
        if (this.nextFilter == null) {
            this.nextFilter = newFilter;
        } else {
            this.nextFilter.addNextFilter(newFilter);
        }
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.filter.FilterInterface#handleRequest(patterns.filter.
     * PayloadInterface)
     */
    @Override
    public void handleRequest(final PayloadInterface payload) {
        if (this.nextFilter != null) {
            this.nextFilter.handleRequest(payload);
        }
    }
}
