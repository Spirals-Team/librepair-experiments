
package patterns.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * class AbstractOutputChannel.
 */
public abstract class AbstractOutputChannel implements OutputChannelInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /*
     * (non-Javadoc)
     *
     * @see patterns.router.OutputChannelInterface#sendMessage(patterns.router.
     * Message)
     */
    @Override
    public void sendMessage(final Message message) {
        this.log.info("{}.sendMessage({})", this.getClass().getSimpleName(), message);
    }

}
