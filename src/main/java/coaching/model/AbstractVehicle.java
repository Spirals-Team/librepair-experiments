/**
 * Created on 28-Jun-2004
 */

package coaching.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract Vehicle class.
 */
public abstract class AbstractVehicle implements VehicleInterface, FuelInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The engine. */
    protected AbstractEngine engine;

    /** The driver. */
    protected Driver driver;

    /**
     * Instantiates a new abstract vehicle.
     *
     * @param engine
     *            the engine
     */
    public AbstractVehicle(final AbstractEngine engine) {
        this.engine = engine;
    }

    @Override
    public void addFuel(int quantity) {
        engine.addFuel(quantity);
    }

    @Override
    public void useFuel(int quantity) {
        engine.useFuel(quantity);
    }
    
    /*
     * (non-Javadoc)
     *
     * @see associations.Vehicle#setDriver(associations.Driver)
     */
    @Override
    public AbstractVehicle setDriver(final Driver driver) {
        this.driver = driver;
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see associations.Vehicle#getDriver()
     */
    @Override
    public Driver getDriver() {
        return this.driver;
    }

    /*
     * (non-Javadoc)
     *
     * @see associations.Vehicle#travel(java.lang.String)
     */
    @Override
    public VehicleInterface travel(final String destination) {
        this.log.info(destination);
        engine.useFuel(1);
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s [engine=%s, driver=%s]", this.getClass().getSimpleName(), engine, driver);
    }

}
