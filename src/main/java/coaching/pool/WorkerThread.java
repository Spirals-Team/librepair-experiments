
package coaching.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WorkerThread Class.
 */
public class WorkerThread implements Runnable {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(WorkerThread.class);

    /** The Constant INTERVAL. */
    private static final int INTERVAL = 5000;

    /** The command. */
    private final String command;

    /**
     * Instantiates a new worker thread.
     *
     * @param commandName
     *            the command name as String object.
     */
    public WorkerThread(final String commandName) {
        this.command = commandName;
    }

    /**
     * Process command.
     */
    private void processCommand() {
        try {
            // Simulate some work
            Thread.sleep(WorkerThread.INTERVAL);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.error(e.toString());
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        final String name = Thread.currentThread().getName();
        WorkerThread.LOG.info("{}:{}", name, this.command);
        processCommand();
        WorkerThread.LOG.info("{}:exit", name);
    }

}
