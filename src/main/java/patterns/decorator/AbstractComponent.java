
package patterns.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import patterns.composite.ComponentInterface;

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
    public ComponentInterface operation() {
        throw new UnsupportedOperationException();
    }

}
