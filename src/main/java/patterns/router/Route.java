
package patterns.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Route a Message, from the input channel to the output channel.
 */
class Route implements InputChannelInterface, OutputChannelInterface {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(Route.class);

    /** The output channel. */
    private OutputChannelInterface outputChannel = null;

    /** The input channel. */
    private InputChannelInterface inputChannel = null;

    /**
     * Instantiates a new route.
     *
     * @param outputChannel
     *            the output channel
     * @param inputChannel
     *            the input channel
     */
    public Route(final OutputChannelInterface outputChannel, final InputChannelInterface inputChannel) {
        super();
        this.outputChannel = outputChannel;
        this.inputChannel = inputChannel;
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.router.OutputChannelInterface#sendMessage(patterns.router.
     * Message)
     */
    @Override
    public void sendMessage(final Message message) {
        LOG.info("sendMessage{}", message);
        outputChannel.sendMessage(message);
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.router.InputChannelInterface#receiveMessage()
     */
    @Override
    public Message receiveMessage() {
        LOG.info("receiveMessage{}");
        return inputChannel.receiveMessage();
    }

}
