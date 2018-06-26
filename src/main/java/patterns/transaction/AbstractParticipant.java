
package patterns.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract Participant class.
 * https://en.wikipedia.org/wiki/ACID
 */
public abstract class AbstractParticipant implements ParticipantInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * Operation One, must be implemented in the final class,
     * otherwise will throw an UnsupportedOperationException.
     *
     * @return the participant interface
     */
    public ParticipantInterface operation1() {
        throw new UnsupportedOperationException();
    }

    /**
     * Operation Two, must be implemented in the final class,
     * otherwise will throw an UnsupportedOperationException.
     *
     * @return the participant interface
     */
    public ParticipantInterface operation2() {
        throw new UnsupportedOperationException();
    }

}
