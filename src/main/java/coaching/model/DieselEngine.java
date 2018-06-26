
package coaching.model;

/**
 * DieselEngine class.
 */
public class DieselEngine extends AbstractEngine implements FuelInterface {

    /**
     * Instantiates a new diesel engine.
     */
    public DieselEngine() {
        super(new Diesel());
    }
}
