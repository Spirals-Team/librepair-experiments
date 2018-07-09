
package coaching.model;

/**
 * PetrolEngine class.
 */
public class PetrolEngine extends AbstractEngine implements FuelInterface {

    /**
     * Instantiates a new petrol engine.
     */
    public PetrolEngine() {
        super(new Petrol());
    }

}
