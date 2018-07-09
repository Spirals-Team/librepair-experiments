
package patterns.chain;

/**
 * Handler Interface.
 */
public interface HandlerInterface {

    /**
     * Handle request.
     *
     * @param request
     *            the request
     */
    void handleRequest(final RequestInterface request);

}
