
package coaching.polymorphism;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract Class providing a polymorphic base.
 */
public abstract class AbstractType implements TypeInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /*
     * (non-Javadoc)
     *
     * @see coaching.polymorphism.TypeInterface#operation()
     */
    @Override
    public TypeInterface operation() {
        this.log.info("{}.operation", this.getClass().getSimpleName());
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s", this.getClass().getSimpleName());
    }

}
