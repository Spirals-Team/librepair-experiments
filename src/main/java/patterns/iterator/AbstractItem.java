
package patterns.iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractItem Class.
 */
public abstract class AbstractItem implements ItemInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

}
