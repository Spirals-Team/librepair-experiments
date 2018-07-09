
package coaching.model;

/**
 * Taxi class.
 */
public class Taxi extends Car {

    /** passenger. */
    private Passenger passenger;

    /**
     * Instantiates a new taxi.
     */
    public Taxi() {
        super(new DieselEngine());
    }

    /**
     * Instantiates a new taxi with engine.
     *
     * @param engine
     *            the engine
     */
    public Taxi(final DieselEngine engine) {
        super(engine);
    }

    /**
     * new passenger.
     *
     * @param passenger
     *            the new passenger
     */
    public void setPassenger(final Passenger passenger) {
        this.passenger = passenger;
    }

    /**
     * passenger.
     *
     * @return the passenger
     */
    public Passenger getPassenger() {
        return passenger;
    }

    /**
     * Clear passenger.
     */
    public void clearPassenger() {
        passenger = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see coaching.model.AbstractVehicle#toString()
     */
    @Override
    public String toString() {
        return String.format("%s [%s[passenger=%s]]", this.getClass().getSimpleName(), super.toString(), passenger);
    }
}
