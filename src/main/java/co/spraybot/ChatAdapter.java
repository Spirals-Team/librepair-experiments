package co.spraybot;

import io.vertx.core.Future;
import io.vertx.core.Verticle;

/**
 * Handles the underlying connection to allow receiving and sending ChatMessages from a variety of chat providers.
 *
 * Ideally your chat connection allows the use of WebSockets and your ChatAdapter will establish a WebSocket connection
 * then send messages to the ChatBot Verticle when that WebSocket is updated. You should also ensure that you send a
 * message to the ChatBot if the underlying chat connection was not shutdown gracefully as a result of a request to
 * stop accepting messages.
 *
 * ### Verticle Messages
 *
 * ChatAdapters are expected to publish and subscribe to many messages as they are the primary form of bi-directional
 * communication with the ChatBot and the User.
 *
 * - address: spraybot.chatbot
 * - op: chatMessageReceived
 * - body: ChatMessage The ChatMessage received from the User
 * - response: no response expected from ChatBot
 *
 * - address: spraybot.chatbot
 * - op: connectionTerminated
 * - body: String Description of why the connection died
 * - response: no response expected from ChatBot
 *
 * - address: spraybot.chatadapter
 * - op: sendChatBotResponse
 * - body: ChatBotResponse The ChatMessage that the ChatBot wants to send back to the User
 * - response: Boolean Whether or not the ChatMessage was sent
 *
 * @author Charles Sprayberry
 * @since 0.1.0
 */
public interface ChatAdapter extends Verticle {

    /**
     * @param chatBotResponse The response the ChatCommand wants to send back to the user
     */
    Future<Boolean> sendChatBotResponse(ChatBotResponse chatBotResponse);

}
