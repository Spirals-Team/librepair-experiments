
package patterns.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * class SubSystem.
 */
public class SubSystem {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * Operation.
     */
    public void operation() {
        this.log.info("%s.operation", toString());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s", this.getClass().getSimpleName());
    }

}
