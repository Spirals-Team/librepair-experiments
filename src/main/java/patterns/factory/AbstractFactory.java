
package patterns.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A factory for creating Abstract objects.
 */
public abstract class AbstractFactory implements FactoryInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /*
     * (non-Javadoc)
     *
     * @see patterns.factory.FactoryInterface#createProductA()
     */
    @Override
    public abstract AbstractProductAlpha createProductA();

    /*
     * (non-Javadoc)
     *
     * @see patterns.factory.FactoryInterface#createProductB()
     */
    @Override
    public abstract AbstractProductBeta createProductB();

}
