
package patterns.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A factory for creating Command objects.
 *
 * Commands are cheap to instantiate.
 */
public final class CommandFactory implements InvokerInterface {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(CommandFactory.class);

    /** The Constant COMMANDS_PROPERTIES. */
    private static final String COMMANDS_PROPERTIES = "commands.properties";

    /** The properties. */
    private final Properties properties = new Properties();

    /**
     * Instantiates a new command factory.
     */
    public CommandFactory() {
        super();
        initialise(COMMANDS_PROPERTIES);
    }

    /**
     * Instantiates a new command factory.
     *
     * @param filename
     *            the filename
     */
    public CommandFactory(final String filename) {
        super();
        initialise(filename);
    }

    /**
     * Initialise.
     *
     * @param filename
     *            the filename
     */
    private void initialise(final String filename) {
        try {
            this.properties.load(inputStream(filename));
            LOG.info("properties = {}", this.properties);
        } catch (final IOException e) {
            LOG.error("{}", e);
        }
    }

    /**
     * Input stream.
     *
     * @param resourceName
     *            the resource name
     * @return the input stream
     */
    private InputStream inputStream(final String resourceName) {
        final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream(resourceName);
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.command.InvokerInterface#execute(java.lang.String)
     */
    @Override
    public ResultInterface execute(final String actionName) throws MissingCommandException {
        if (actionName != null) {
            if (actionName.length() > 0) {
                return executeActionName(actionName);
            } else {
                final String message = String.format("actionName '%s' cannot be zero length.", actionName);
                throw new MissingCommandException(message);
            }
        } else {
            final String message = "actionName cannot be null";
            throw new MissingCommandException(message);
        }
    }

    /**
     * Execute action name.
     *
     * @param actionName
     *            the action name
     * @return the result interface
     * @throws MissingCommandException
     *             the missing command exception
     */
    private ResultInterface executeActionName(final String actionName) throws MissingCommandException {
        final String className = this.properties.getProperty(actionName);
        if (className != null) {
            if (className.length() > 0) {
                return executeByClassName(className);
            } else {
                final String message = String.format("className '%s' cannot be zero length.", className);
                throw new MissingCommandException(message);
            }
        } else {
            final String message = String.format("className '%s' cannot be zero length.", className);
            throw new MissingCommandException(message);
        }
    }

    /**
     * Execute by class name.
     *
     * @param className
     *            the class name
     * @return the result interface
     * @throws MissingCommandException
     *             the missing command exception
     */
    private ResultInterface executeByClassName(final String className) throws MissingCommandException {
        AbstractCommand action = null;
        try {
            action = (AbstractCommand) Class.forName(className).newInstance();
            if (action != null) {
                final Parameters commandParameters = new Parameters();
                return action.execute(commandParameters);
            } else {
                final String message = String.format("%s Class not found. ", className);
                throw new MissingCommandException(message);
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new MissingCommandException(e);
        }
    }
}
