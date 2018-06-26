
package patterns.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract Input Channel class.
 */
public abstract class AbstractInputChannel implements InputChannelInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /*
     * (non-Javadoc)
     *
     * @see patterns.router.InputChannelInterface#receiveMessage()
     */
    @Override
    public Message receiveMessage() {
        this.log.info("{}.receiveMessage()", this.getClass().getSimpleName());
        return new Message();
    }

}
