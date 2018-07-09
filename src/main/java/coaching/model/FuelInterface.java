
package coaching.model;

/**
 * Fuel Interface.
 */
public interface FuelInterface {

    /**
     * Adds the fuel.
     *
     * @param quantity the quantity
     */
    void addFuel(final int quantity);

    /**
     * Use fuel.
     *
     * @param quantity the quantity
     */
    void useFuel(final int quantity);
}
