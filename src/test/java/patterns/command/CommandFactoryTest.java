
package patterns.command;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * The CommandFactoryTest class.
 */
public class CommandFactoryTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(CommandFactoryTest.class);

    /**
     * Unit Test to command factory.
     *
     */
    @Test
    public void testCommandFactory() {
        LOG.debug("testCommandFactory");
        final InvokerInterface commandFactory = new CommandFactory();
        assertNotNull(commandFactory);
        LOG.info(commandFactory.toString());
    }

    /**
     * Unit Test to command factory file.
     *
     */
    @Test
    public void testCommandFactoryFile() {
        LOG.debug("testCommandFactoryFile");
        final InvokerInterface commandFactory = new CommandFactory("commands.properties");
        assertNotNull(commandFactory);
        LOG.info(commandFactory.toString());
    }

    /**
     * Unit Test to missing command execute.
     *
     * @throws Exception
     *             the exception
     */
    @Test(expected = MissingCommandException.class)
    public void testMissingCommandExecute() throws Exception {
        LOG.debug("testMissingCommandExecute");
        final InvokerInterface commandFactory = new CommandFactory();
        assertNotNull(commandFactory);
        final String actionName = "MissingCommand";
        commandFactory.execute(actionName);
    }

    /**
     * Unit Test to execute missing command.
     *
     * @throws Exception
     *             the exception
     */
    @Test(expected = MissingCommandException.class)
    public void testExecuteMissingCommand() throws Exception {
        LOG.debug("testExecuteMissingCommand");
        final InvokerInterface commandFactory = new CommandFactory();
        assertNotNull(commandFactory);
        final String actionName = "MissingCommand";
        commandFactory.execute(actionName);
    }

    /**
     * Unit Test to execute missing class.
     *
     * @throws Exception
     *             the exception
     */
    @Test(expected = MissingCommandException.class)
    public void testExecuteMissingClass() throws Exception {
        LOG.debug("testExecuteMissingClass");
        final InvokerInterface commandFactory = new CommandFactory();
        assertNotNull(commandFactory);
        final String actionName = "MissingCommand";
        commandFactory.execute(actionName);
    }

    /**
     * Unit Test to execute example command.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testExecuteExampleCommand() throws Exception {
        LOG.debug("testExecuteExampleCommand");
        final InvokerInterface commandFactory = new CommandFactory();
        assertNotNull(commandFactory);
        final String actionName = "ExampleCommand";
        final ResultInterface result = commandFactory.execute(actionName);
        assertNotNull(result);
        LOG.info(result.toString());
    }

    /**
     * Unit Test to execute sequence command.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testExecuteSequenceCommand() throws Exception {
        LOG.debug("testExecuteSequenceCommand");
        final InvokerInterface commandFactory = new CommandFactory();
        assertNotNull(commandFactory);
        final String actionName = "SequenceCommand";
        final ResultInterface result = commandFactory.execute(actionName);
        assertNotNull(result);
        LOG.info(result.toString());
    }

    /**
     * Unit Test to execute compound command.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testExecuteCompoundCommand() throws Exception {
        LOG.debug("testExecuteCompoundCommand");
        final InvokerInterface commandFactory = new CommandFactory();
        assertNotNull(commandFactory);
        final String actionName = "CompoundCommand";
        final ResultInterface result = commandFactory.execute(actionName);
        assertNotNull(result);
        LOG.info(result.toString());
    }

    /**
     * Unit Test to execute conditional command.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testExecuteConditionalCommand() throws Exception {
        LOG.debug("testExecuteConditionalCommand");
        final InvokerInterface commandFactory = new CommandFactory();
        assertNotNull(commandFactory);
        final String actionName = "ConditionalCommand";
        final ResultInterface result = commandFactory.execute(actionName);
        assertNotNull(result);
        LOG.info(result.toString());
    }

}
