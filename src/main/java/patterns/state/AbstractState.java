
package patterns.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractState class.
 */
public abstract class AbstractState {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * Handle the state, do what needs to be done when this state arises.
     */
    public abstract void handle();

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
