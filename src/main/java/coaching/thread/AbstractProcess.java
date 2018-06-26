
package coaching.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**

 * An abstract base class for an Application.
 *  @author         martin.spamer.
 *  @version        0.1 - first release.
 *  Created         17-Sep-2004 - 16:13:19
 */
public abstract class AbstractProcess implements Runnable {

    /** The Constant MAX_TICKS. */
    private static final int MAX_TICKS = 10;

    /** The Constant DEFAULT_WAIT. */
    private static final int DEFAULT_WAIT = 1000;

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The thread. */
    private final Thread thread;

    /** The tick. */
    private long tick;

    /** The exit. */
    private boolean exit = false;

    /**
     * AbstractProcess.
     */
    public AbstractProcess() {
        this.log.info("AbstractProcess()", this.getClass().getSimpleName());
        this.thread = new Thread(this);
    }

    /**
     * Start the thread running.
     */
    public void start() {
        this.log.info("{}.start()", this.getClass().getSimpleName());
        this.thread.start();
    }

    /**
     * thread executes when it receives a time thread this function should
     * simple
     * exit.
     */
    @Override
    public void run() {
        this.log.info("{}.run", this.getClass().getSimpleName());
        try {
            do {
                // A Run method MUST have either a sleep or yield to prevent
                // deadlock.

                // Pause for 1 Second.
                Thread.sleep(DEFAULT_WAIT); // Note sleep is static method.

                // Pause until I'm allowed to continue.
                Thread.yield(); // Note that yield is a static method.

                // Thread ends if it runs more than a ten times.
                // alternatively I could throw a new InterruptedException
                this.tick++;
                if (this.tick >= MAX_TICKS) {
                    this.exit = true;
                }

                this.log.info("tick={}", this.tick);
            } while (!this.exit);

        } catch (final InterruptedException exception) {
            this.log.error("{}", exception.toString());
        }
        this.log.info("{}.ending", this.getClass().getSimpleName());
    }

    /**
     * Stop the thread running.
     */
    public void stop() {
        this.log.info("{}.stop()", this.getClass().getSimpleName());
        this.exit = true;
    }
}
