
package patterns.command;

import coaching.context.AbstractContext;
import coaching.context.ContextInterface;

/**
 * Context Class.
 */
public final class CommandContext extends AbstractContext {

    /**
     * single instance within classLoader.
     */
    private static ContextInterface instance;

    /**
     * Constructor is private to prevent wild construction.
     */
    private CommandContext() {
        super();
    }

    /**
     * single instance.
     *
     * @return single instance of Context single instance
     */
    public static synchronized ContextInterface getInstance() {
        if (instance == null) {
            instance = CommandContext.create();
        }
        return instance;
    }

    /**
     * Creates the.
     *
     * @return the context interface
     */
    public static ContextInterface create() {
        return new CommandContext();
    }
}
