
package patterns.router;

/**
 * Input Channel Interface.
 */
public interface InputChannelInterface {

    /**
     * Receive message.
     *
     * @return the message
     */
    Message receiveMessage();

}
