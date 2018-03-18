package co.spraybot;

import io.vertx.core.Future;

/**
 * Simplifies typical interactions that a ChatCommand would have with the ChatBot and its subsystems.
 *
 * Although our architecture leads to a highly-scalable, distributed system it can also result in a somewhat burdensome
 * use case for userland code. Passing around messages requires userland code to keep track of addresses, the expected
 * message type, expected headers, and to handle getting the response back, if appropriate. The BasicChatCommandDrone facilitates
 * performing these operations in a much simpler interface.
 *
 * Typically your ChatCommand implementations should only ever interact with the BasicChatCommandDrone and never with the underlying,
 * low-level subsystems. You should not construct a BasicChatCommandDrone yourself, if you are in need of the BasicChatCommandDrone outside of the
 * ChatCommand context you should use the StdDroneFactory. Alternatively, you could also use spraybot's guice injector.
 *
 * @author Charles Sprayberry
 * @since 0.1.0
 * @see ChatMessage
 */
public interface ChatCommandDrone extends HardDriveAwareDrone {

    /**
     * @return The name of the ChatBot that this Drone works for
     */
    String getChatBotName();

    /**
     * @return The ChatMessage that was sent by the User that activate this ChatCommand
     */
    ChatMessage getChatMessage();

    /**
     * Send a response to the ChatMessage from getChatMessage; the underlying chat adapter should send either to the room
     * the ChatMessage originated from or a direct message. The response should directly mention or notify the user in
     * the response.
     *
     * @param chatMessageResponse The content you want to send in response to the ChatMessage
     * @return Whether or not the message was sent
     */
    Future<Boolean> sendResponse(String chatMessageResponse);

    /**
     *
     * @param directMessageResponse
     * @return
     */
    Future<Boolean> sendResponseAsDirectMessage(String directMessageResponse);

}
