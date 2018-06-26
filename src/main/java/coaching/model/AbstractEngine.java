
package coaching.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract Engine class.
 */
public abstract class AbstractEngine implements FuelInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The fuel. */
    private FuelInterface fuel;

    /**
     * Instantiates a new abstract engine.
     *
     * @param fuel
     *            the fuel
     */
    public AbstractEngine(final FuelInterface fuel) {
        super();
        fuelType(fuel);
    }

    /**
     * Use fuel type.
     *
     * @param fuel
     *            the fuel
     */
    private void fuelType(final FuelInterface fuel) {
        this.fuel = fuel;
    }

    @Override
    public void addFuel(int quantity) {
        fuel.addFuel(quantity);
    }

    @Override
    public void useFuel(int quantity) {
        fuel.useFuel(quantity);
    }

    @Override
    public String toString() {
        return String.format("%s [fuel=%s]", this.getClass().getSimpleName(), fuel);
    }

}
