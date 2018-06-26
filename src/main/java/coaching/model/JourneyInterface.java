
package coaching.model;

/**
 * Journey Interface.
 */
public interface JourneyInterface {

    /**
     * set the destination of the journey.
     *
     * @param destination
     *            the new destination
     * @return this as fluent interface.
     */
    JourneyInterface setDestination(final String destination);

    /**
     * get the destination of the journey.
     *
     * @return the destination
     */
    String getDestination();

}
