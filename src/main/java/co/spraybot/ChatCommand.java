package co.spraybot;

import io.vertx.core.Future;

/**
 * A discrete action or set of functionality that can be assigned to a ChatBot.
 *
 * @author Charles Sprayberry
 * @since 0.1.0
 * @see ChatMessage
 * @see ChatCommandDrone
 */
public interface ChatCommand {

    /**
     * Ideally the name of this ChatCommand should be namespaced as to not conflict with other possible Commands from
     * different libraries.
     *
     * @return A unique name that identifies the ChatCommand
     */
    String getName();

    /**
     * Do whatever it is that your ChatCommand is meant to do.
     *
     * @param chatCommandDrone The ChatCommandDrone to help facilitate communication with the ChatBot subsystems
     * @return Complete or fail the Future depending on if the ChatCommand completed successfully or not
     */
    Future<Void> perform(ChatCommandDrone chatCommandDrone);

}
