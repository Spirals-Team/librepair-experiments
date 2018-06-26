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
    private static final Logger log = LoggerFactory.getLogger(AssociationsTest.class);

    /**
     * Unit Test for Car example.
     * 
     * A Car has an engine from construction.
     */
    @Test
    public void testExampleComposition() {
        log.info("testExampleComposition");
        final VehicleInterface car = new Car();
        assertNotNull("Value cannot be null", car);
        log.info("car = {}", car);
    }

    /**
     * Example aggregation.
     * 
     * The driver changes.
     */
    @Test
    public void testExampleAggregation() {
        // Given
        log.info("testExampleAggregation");
        final Car car = new Car();
        assertNotNull("Value cannot be null", car);

        // When
        final Driver driver = new Driver("Alice");
        assertNotNull("Value cannot be null", driver);
        car.setDriver(driver);
        log.info("car = {}", car);
    }

    /**
     * Unit Test for Truck example.
     */
    @Test
    public void testExampleTruck() {
        // Given
        log.info("testExampleTruck");
        final Truck truck = new Truck();
        assertNotNull("Value cannot be null", truck);

        // When
        final Driver driver = new Driver("Alice");
        assertNotNull("Value cannot be null", driver);

        truck.setDriver(driver);
        log.info("truck = {}", truck);
    }
    
    /**
     * Unit Test to example taxi.
     */
    @Test
    public void testExampleTaxi() {
        // Given
        log.info("testExampleTaxi");
        final Taxi taxi = new Taxi();
        assertNotNull("Value cannot be null", taxi);

        final Driver driver = new Driver("Alice");
        assertNotNull("Value cannot be null", driver);
        taxi.setDriver(driver);
        
        // When
        final Passenger passenger = new Passenger("Bob");
        assertNotNull("Value cannot be null", passenger);        
        taxi.setPassenger(passenger);
        log.info("taxi = {}", taxi);
        taxi.clearPassenger();
        log.info("taxi = {}", taxi);
    }

    /**
     * Unit Test to example motor cycle rider.
     */
    @Test
    public void testExampleMotorCycleRider() {
        // Given
        log.info("testExampleMotorCycleRider");
        final MotorCycle motorCycle = new MotorCycle();
        assertNotNull("Value cannot be null", motorCycle);

        // When
        final Rider rider = new Rider("Charlie");
        assertNotNull("Value cannot be null", rider);
        motorCycle.setRider(rider);
        log.info("motorCycle = {}", motorCycle);
    }

}
