package co.spraybot;

import io.vertx.core.Future;
import io.vertx.core.Verticle;

/**
 * Reponsible for ensuring that each ChatCommand the ChatBot requests to be executed performs and failures are gracefully
 * handled.
 *
 * ### Verticle Messages
 *
 * - address: spraybot.chatcommandrunner
 * - op: performCommand
 * - body: ChatMessage
 * - response: Void Will respond with a success or failure based on ChatCommand execution
 *
 * @author Charles Sprayberry
 * @since 0.1.0
 */
public interface ChatCommandRunner extends Verticle {

    /**
     * @param chatCommand A ChatCommand that this ChatCommandRunner will know how to execute if its name is given to performCommand
     */
    void registerCommand(ChatCommand chatCommand);

    /**
     * @param commandName The name of the ChatCommand associated to the pat
     * @param handler Action you want to execute when the ChatBot requests you to perform a command matching commandName
     */
    void registerCommand(String commandName, ChatCommandHandler handler);

    /**
     * @param commandName The commandName you want to see if has been registered with this ChatCommandRunner
     * @return Whether or not a ChatCommand matching the commandName has been registered
     */
    boolean canPerformCommand(String commandName);

    /**
     * @param commandName The name of the ChatCommand that should be invoked
     * @return Complete or fail the Future based on whether or not the ChatCommand could be found and executed
     */
    Future<Void> performCommand(String commandName, ChatMessage chatMessage);

}
