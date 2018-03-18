package co.spraybot;

/**
 * A User, NOT the ChatBot, of the chat platform the ChatBot is connected to.
 *
 * @author Charles Sprayberry
 * @since 0.1.0
 */
public interface User {

    /**
     * @return The ChatAdapter specific ID for the underlying User that the ChatBot is interacting with.
     */
    String getId();

    /**
     * @return The name by which the ChatBot should address the User.
     */
    String getName();

}
