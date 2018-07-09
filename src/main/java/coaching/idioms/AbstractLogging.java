
package coaching.idioms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An AbstractLogging logging class.
 *
 * a protected logger can be used by any inheriting specialisation class.
 *
 * Each Class has its own logger, but uses this.getClass().getSimpleName() to
 * ensure polymorphic sub-classes always log under their name.
 *
 * logging layout formater (%F:%L) for click through in most IDEs.
 */
public abstract class AbstractLogging implements LoggingInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /*
     * (non-Javadoc)
     *
     * @see coaching.idioms.LoggingInterface#toLog()
     */
    @Override
    public void toLog() {
        this.log.info("the.{}.toLog", this.getClass().getSimpleName());
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.idioms.LoggingInterface#logTo(org.slf4j.Logger)
     */
    @Override
    public void logTo(final Logger destinationLog) {
        final String destinationLogName = destinationLog.getClass().getSimpleName();
        destinationLog.info("{}.log({})", destinationLogName, this.toString());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s [...]", this.getClass().getSimpleName());
    }

}
