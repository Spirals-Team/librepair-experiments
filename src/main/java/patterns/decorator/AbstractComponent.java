
package patterns.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractComponent Class.
 */
public abstract class AbstractComponent implements ComponentInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /*
     * (non-Javadoc)
     *
     * @see patterns.decorator.ComponentInterface#operation()
     */
    @Override
    public AbstractComponent operation() {
        String simpleName = this.getClass().getSimpleName();
        this.log.info("{}", simpleName);
        return this;
    }

}
