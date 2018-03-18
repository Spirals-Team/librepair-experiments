package co.spraybot;

import io.vertx.core.Future;

/**
 * Provides a simple way to add Commands to a ChatCommandRunner without requiring the creation of a full blown ChatCommand
 * object.
 *
 * While it is possible to implement your own ChatCommand object that wraps this it is recommended that you utilize the
 * ChatCommandRunner directly. For example:
 * <pre>
 *     ChatCommandRunner commandRunner = YourCommandRunnerFactory.create();
 *     commandRunner.registerCommand("commandName", drone -> {
 *         // do whatever you want with the drone
 *     });
 * </pre>
 *
 * Now anytime the ChatBot finds a ChatMessage that matches a pattern for "commandName" your ChatCommandHandler will
 * execute.
 *
 * @author Charles Sprayberry
 * @since 0.1.0
 * @see ChatCommandDrone
 * @see ChatCommandRunner
 */
@FunctionalInterface
public interface ChatCommandHandler {

    /**
     * Perform whatever action you'd like to perform for the given ChatCommand.
     *
     * @param chatCommandDrone A facade simplifying interactions with the ChatBot, HardDrive and ChatAdapter
     * @return Ensure that your ChatCommand is asynchronous and completes the Future when done
     */
    Future<Void> handle(ChatCommandDrone chatCommandDrone);

}
