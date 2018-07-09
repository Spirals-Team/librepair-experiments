/**
 * VehicleInterface.
 * Created on 06-Jul-2004
 */

package coaching.model;

/**
 * Vehicle Interface.
 */
public interface VehicleInterface {

    /**
     * set the vehicle's driver.
     *
     * @param driver
     *            the new
     * @return this for a fluent interface.
     */
    VehicleInterface setDriver(final Driver driver);

    /**
     * driver.
     *
     * @return the driver
     */
    Driver getDriver();

    /**
     * Travel destination.
     *
     * @param destination
     *            the destination
     * @return this for a fluent interface.
     */
    VehicleInterface travel(final String destination);

}
