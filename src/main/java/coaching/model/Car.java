/**
 * Created on 28-Jun-2004
 *
 */

package coaching.model;

/**
 * An example Car class.
 */
public class Car extends AbstractVehicle implements CarInterface {

    /**
     * Instantiates a new car.
     */
    public Car() {
        super(new PetrolEngine());
    }

    /**
     * Instantiates a new car with an engine.
     *
     * @param engine
     *            the engine
     */
    public Car(final AbstractEngine engine) {
        super(engine);
    }

}
