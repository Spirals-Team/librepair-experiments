package world.bentobox.bentobox.api.commands;

import java.util.List;
import java.util.Optional;

import world.bentobox.bentobox.api.user.User;

/**
 * Interface for BentoBox Commands
 * @author tastybento
 *
 */
public interface BentoBoxCommand {

    /**
     * Anything that needs to be set up for this command.
     * Register subcommands in this section.
     */
    void setup();

    /**
     * What will be executed when this command is run
     * @param user the User who is executing the command
     * @param label the label which has been used to execute the command (can be the command's label OR an alias)
     * @param args the command arguments
     * @return true or false - true if the command executed successfully
     */
    boolean execute(User user, String label, List<String> args);

    /**
     * Tab Completer for CompositeCommands. Note that any registered sub-commands will be automatically
     * added to the list must not be manually added. Use this to add tab-complete for things like names.
     * @param user - the User
     * @param alias - alias for command
     * @param args - command arguments
     * @return List of strings that could be used to complete this command.
     */
    default Optional<List<String>> tabComplete(User user, String alias, List<String> args) {
        return Optional.empty();
    }

}
