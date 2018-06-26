/**
 * Created on 05-Jul-2004
 */

package coaching.model;

/**
 * Truck class.
 */
public class Truck extends AbstractVehicle {

    /**
     * Instantiates a new truck.
     */
    public Truck() {
        super(new DieselEngine());
    }

}
