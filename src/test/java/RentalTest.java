import model.Rental;
import model.User;
import model.Vehicle;
import model.builder.RentalBuilder;
import model.builder.UserBuilder;
import model.builder.VehicleBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Created by mariano on 08/04/18.
 */
public class RentalTest {


    @Test
    public void  rentalConstructorTest(){
        Date date = new Date();
        User userOwner = new UserBuilder().setName("Pepe").build();
        User userClient = new UserBuilder().setName("Lolo").build();
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.TRUCK).build();
        Rental rental = new RentalBuilder().setId(2l).setStartDate(date).setOwner(userOwner)
                .setClient(userClient).setVehicle(vehicle).build();
        rental.setEndDate(date);
        Assert.assertTrue(rental.getStartDate().equals(date) && rental.getEndDate().equals(date)
                && rental.getOwner().equals(userOwner) && rental.getClient().equals(userClient)
                && rental.getVehicle().equals(vehicle) && rental.getState().equals(Rental.RentalState.WAIT_CONFIRM));
    }


    @Test
    public void initRentalChangeStateToInProgressTest(){
        User userOwner = new UserBuilder().setName("Pepe").build();
        User userClient = new UserBuilder().setName("Lolo").build();
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.VAN).build();
        Rental rental = new Rental(userOwner, userClient, vehicle);
        Assert.assertTrue(rental.getState().equals(Rental.RentalState.WAIT_CONFIRM));
        rental.initRental();
        Assert.assertTrue(rental.getState().equals(Rental.RentalState.IN_PROGRESS));
    }


    @Test
    public void cancelRentalChangeStateToCancelTest(){
        User userOwner = new UserBuilder().setName("Charlie").build();
        User userClient = new UserBuilder().setName("Vladimir").build();
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.TRUCK).build();
        Rental rental = new RentalBuilder().setOwner(userOwner).setClient(userClient).setVehicle(vehicle).build();
        Assert.assertTrue(rental.getState().equals(Rental.RentalState.WAIT_CONFIRM));
        rental.cancelRental();
        Assert.assertTrue(rental.getState().equals(Rental.RentalState.CANCEL));
    }




}
