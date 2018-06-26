
package patterns.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract Strategy Class.
 */
public abstract class AbstractStrategy implements StrategyInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The context. */
    private Context context = null;

    /**
     * Instantiates a new abstract strategy.
     *
     * @param context
     *            the context
     */
    public AbstractStrategy(final Context context) {
        super();
        this.context = context;
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.strategy.AbstractStrategy#operation()
     */
    @Override
    public void operation() {
        this.log.info("{}.operation() should be overridden.", this.getClass().getSimpleName());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("AbstractStrategy [context=%s]", this.context);
    }

}
