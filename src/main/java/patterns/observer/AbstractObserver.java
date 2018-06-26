
package patterns.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An asynchronous update interface for receiving notifications about Abstract
 * Abstract is constructed.
 */
public abstract class AbstractObserver implements ObserverInterface {

    /** provides logging. */
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /*
     * (non-Javadoc)
     *
     * @see patterns.observer.ObserverInterface#updateObservers()
     */
    @Override
    public void updateObservers() {
        this.log.info("update notification received");
    }

}
