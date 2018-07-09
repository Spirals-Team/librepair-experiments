
package patterns.command;

import java.util.concurrent.ConcurrentHashMap;

/**
 * CommandMap Class.
 *
 * Commands are expensive to construct borrow them from resource pool and return
 * when finished.
 */
@SuppressWarnings("serial")
public final class CommandMap extends ConcurrentHashMap<String, AbstractCommand> {

    /**
     * Execute.
     *
     * @param actionName
     *            the action name
     * @return the result interface
     * @throws MissingCommandException
     *             the missing command exception
     */
    public ResultInterface execute(final String actionName) throws MissingCommandException {
        if (actionName == null) {
            final String message = "actionName cannot be null";
            throw new MissingCommandException(message);
        } else {
            final CommandInterface command = get(actionName);
            if (command == null) {
                final String message = String.format("command %s not found", actionName);
                throw new MissingCommandException(message);
            } else {
                return command.execute(new Parameters());
            }
        }
    }
}
