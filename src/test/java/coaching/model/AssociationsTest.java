/**
 * Created on 05-Jul-2004
 */

package coaching.model;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

public class AssociationsTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(AssociationsTest.class);

    /**
     * Unit Test for Car example.
     *
     * A Car has an engine from construction.
     */
    @Test
    public void testExampleComposition() {
        LOG.info("testExampleComposition");
        final VehicleInterface car = new Car();
        assertNotNull(car);
        LOG.info("car = {}", car);
    }

    /**
     * Example aggregation.
     *
     * The driver changes.
     */
    @Test
    public void testExampleAggregation() {
        // Given
        LOG.info("testExampleAggregation");
        final Car car = new Car();
        assertNotNull(car);

        // When
        final Driver driver = new Driver("Alice");
        assertNotNull(driver);
        car.setDriver(driver);
        LOG.info("car = {}", car);
    }

    /**
     * Unit Test for Truck example.
     */
    @Test
    public void testExampleTruck() {
        // Given
        LOG.info("testExampleTruck");
        final Truck truck = new Truck();
        assertNotNull(truck);

        // When
        final Driver driver = new Driver("Alice");
        assertNotNull(driver);

        truck.setDriver(driver);
        LOG.info("truck = {}", truck);
    }

    /**
     * Unit Test to example taxi.
     */
    @Test
    public void testExampleTaxi() {
        // Given
        LOG.info("testExampleTaxi");
        final Taxi taxi = new Taxi();
        assertNotNull(taxi);

        final Driver driver = new Driver("Alice");
        assertNotNull(driver);
        taxi.setDriver(driver);

        // When
        final Passenger passenger = new Passenger("Bob");
        assertNotNull(passenger);
        taxi.setPassenger(passenger);
        LOG.info("taxi = {}", taxi);
        taxi.clearPassenger();
        LOG.info("taxi = {}", taxi);
    }

    /**
     * Unit Test to example motor cycle rider.
     */
    @Test
    public void testExampleMotorCycleRider() {
        // Given
        LOG.info("testExampleMotorCycleRider");
        final MotorCycle motorCycle = new MotorCycle();
        assertNotNull(motorCycle);

        // When
        final Rider rider = new Rider("Charlie");
        assertNotNull(rider);
        motorCycle.setRider(rider);
        LOG.info("motorCycle = {}", motorCycle);
    }

}
