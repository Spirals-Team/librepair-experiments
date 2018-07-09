
package patterns.filter;

/**
 * Filter Interface.
 */
public interface FilterInterface {

    /**
     * next filter.
     *
     * @param newFilter
     *            the new filter
     * @return the filter interface
     */
    FilterInterface addNextFilter(final FilterInterface newFilter);

    /**
     * handleRequest.
     *
     * @param payload
     *            the payload
     */
    void handleRequest(final PayloadInterface payload);

}
