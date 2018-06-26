
package patterns.router;

/**
 * Output Channel Interface.
 */
public interface OutputChannelInterface {

    /**
     * Send message.
     *
     * @param message
     *            the message
     */
    void sendMessage(final Message message);

}
