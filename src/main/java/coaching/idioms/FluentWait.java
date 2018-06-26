
package coaching.idioms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Fluent Wait Class, will test a condition for one second.
 */
public class FluentWait {

    /** The Constant DEFAULT_INTERVAL. */
    private static final int DEFAULT_INTERVAL = 100;

    /** The Constant DEFAULT_TIMEOUT. */
    private static final int DEFAULT_TIMEOUT = 1000;

    /** The time out. */
    private long timeOut = DEFAULT_TIMEOUT;

    /** The interval. */
    private long interval = DEFAULT_INTERVAL;

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * Set the time out.
     *
     * @param timeout
     *            the timeout
     * @return the fluent wait
     */
    public FluentWait setTimeOut(final long timeout) {
        this.timeOut = timeout;
        return this;
    }

    /**
     * Time out.
     *
     * @return the timeOut as long
     */
    public long setTimeOut() {
        return this.timeOut;
    }

    /**
     * set the interval.
     *
     * @param interval
     *            the interval
     * @return the fluent wait
     */
    public FluentWait setInterval(final long interval) {
        this.interval = interval;
        return this;
    }

    /**
     * Interval.
     *
     * @return the interval as native long.
     */
    public long getInterval() {
        return this.interval;
    }

    /**
     * Until.
     *
     * @param condition
     *            the condition
     * @return true, if successful, otherwise false., otherwise false.
     */
    public boolean until(final Condition condition) {
        if (condition != null) {
            final long startTime = System.currentTimeMillis();
            final long endTime = startTime + this.timeOut;
            do {
                if (condition.isTrue()) {
                    this.log.info("pass at {}", System.currentTimeMillis());
                    return true;
                } else {
                    try {
                        this.log.info("sleep {} at {}", this.interval, System.currentTimeMillis());
                        Thread.sleep(this.interval);
                    } catch (final InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            } while (System.currentTimeMillis() < endTime);
            this.log.info("timeout at {}", System.currentTimeMillis());
        } else {
            this.log.info("null condition at {}", System.currentTimeMillis());
        }
        return false;
    }

}
