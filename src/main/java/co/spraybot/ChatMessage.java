package co.spraybot;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Represents a discrete interaction between a chat user and the ChatBot, where the user has sent a chat message to the
 * underlying ChatAdapter. The ChatBot may or may not have a corresponding ChatCommand associated with the body of the
 * ChatMessage.
 *
 * @author Charles Sprayberry
 * @since 0.1.0
 */
public interface ChatMessage {

    /**
     * This value is provided for informational purposes only; creating instances of the returned ChatAdapter based on
     * this is HIGHLY DISCOURAGED.
     *
     * @return The ChatAdapter type that powers this underlying message
     */
    String getAdapterName();

    /**
     * Headers set by the ChatAdapter upon ChatMessage receipt to ensure whatever ChatAdapter specific information is
     * necessary to process requests can be stored.
     *
     * Typically it is not expected that ChatCommand objects would interact with this directly. However, if you do it is
     * highly recommended you namespace whatever data you have with your application or organization name. Additionally
     * ChatAdapter implementations should also namespace whatever attributes are added. For example if you wanted to
     * store the room ID for a Slack adapter it could be stored as `slack.room-id`.
     *
     * The returned Map should be immutable, preferably throwing an error if a mutable operation is used; see Guava's
     * immutable collections for an example of this. The default ChatMessageBuilder utilizes this pattern. At the very
     * least if you allow mutable operations on the Map returned it should not mutate the actual ChatMessage itself.
     *
     * @return An arbitrary collection of headers associated with the ChatMessage
     */
    Map<String, String> getHeaders();

    /**
     * @return The contents of the ChatMessage the User sent to the ChatBot.
     */
    String getBody();

    /**
     * If the pattern registered with the ChatBot that triggered this ChatCommand included dynamic attributes they will
     * be included in this Map.
     *
     * The key of the Map is the name of the parameter and the value of that key is, well, the value of the parameter.
     * For example, if your ChatCommand pattern looked like:
     *
     * `foo bar {baz} qux foobar {foobaz}`
     *
     * And a ChatMessage was received with the body:
     *
     * `foo bar spraybot, the chatbot powered by vert.x, qux foobar will parse nonsensical messages`
     *
     * Would result in a Map with the following data:
     *
     * baz = "spraybot, the chatbot powered by vert.x,"
     * foobaz = "will parse nonsensical messages"
     *
     * The returned Map should be immutable, preferably throwing an error if a mutable operation is used; see Guava's
     * immutable collections for an example of this. The default ChatMessageBuilder utilizes this pattern. At the very
     * least if you allow mutable operations on the Map returned it should not mutate the actual ChatMessage itself.
     *
     * @return The attributes that were parsed out of the body contents
     */
    Map<String, String> getBodyAttributes();

    /**
     * @return The name of the room this message was received in.
     */
    String getRoomName();

    /**
     * @return The time at which this ChatMessage was received from the User.
     */
    LocalDateTime getTimestamp();

    /**
     * @return The User the ChatBot is communicating with.
     */
    User getUser();

    /**
     * @return Whether this is a direct message between the User and the ChatBot
     */
    boolean isDirectMessage();

    /**
     * Returns a builder that is a preferred way to create ChatMessage instances.
     *
     * @return A builder to allow easily constructing ChatMessages
     */
    static ChatMessageBuilder builder() {
        return new ChatMessageBuilder();
    }

}
