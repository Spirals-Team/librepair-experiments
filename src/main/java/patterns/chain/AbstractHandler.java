
package patterns.chain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract Handler Class.
 */
public abstract class AbstractHandler implements HandlerInterface {

    /** The next. */
    private HandlerInterface next;

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * Instantiates a new abstract handler.
     */
    public AbstractHandler() {
        super();
    }

    /**
     * Instantiates a new handler.
     *
     * next
     *
     * @param next
     *            the next
     */
    public AbstractHandler(final HandlerInterface next) {
        super();
        setNext(next);
    }

    /**
     * next.
     *
     * next handler
     *
     * @param next
     *            the next
     * @return the abstract handler
     */
    public AbstractHandler setNext(final HandlerInterface next) {
        this.next = next;
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.chain.HandlerInterface#handleRequest()
     */
    @Override
    public void handleRequest(final RequestInterface request) {
        this.next.handleRequest(request);
    }

}
