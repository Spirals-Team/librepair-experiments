
package coaching.delegation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Worker Class.
 */
public class Worker implements ProcessInterface {

    /** provides logging. */
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /*
     * (non-Javadoc)
     *
     * @see delegation.ProcessInterface#doProcess()
     */
    @Override
    public Worker doProcess() {
        log.info("do work");
        return this;
    }

}
