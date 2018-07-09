
package patterns.filter;

/**
 * Filter Manager Class.
 */
public final class FilterManager {

    /**
     * Operation that needs to be filtered.
     */
    public void operation() {
        final FilterInterface first = new FilterAlice();
        first.addNextFilter(new FilterBob());
        first.addNextFilter(new FilterCharlie());
        first.handleRequest(new Payload());
    }

}
