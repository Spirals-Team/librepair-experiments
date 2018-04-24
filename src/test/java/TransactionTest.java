import model.Rental;
import model.Transaction;
import model.User;
import model.Vehicle;
import model.builder.RentalBuilder;
import model.builder.TransactionBuilder;
import model.builder.UserBuilder;
import model.builder.VehicleBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Created by mariano on 08/04/18.
 */
public class TransactionTest {


    @Test
    public void  transactionConstructorTest(){
        Date date = new Date();
        User userOwner = new UserBuilder().setName("Vladimir").build();
        User userClient = new UserBuilder().setName("Charlie").build();
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.CAR).build();
        Rental rental = new RentalBuilder().setId(2222l).setStartDate(date).setEndDate(date).setOwner(userOwner)
                .setClient(userClient).setVehicle(vehicle).build();
        Transaction transaction = new Transaction(500, rental);
        Assert.assertTrue(transaction.getCost().equals(500) && transaction.getRental().equals(rental)
                && transaction.getState().equals(Transaction.StateTransaction.RESERVATION));
    }


    @Test
    public void cancelTransactionChangeStateToCancelAndRentalStateInCancel(){
        Date date = new Date();
        User userOwner = new UserBuilder().setName("Vladimir").build();
        User userClient = new UserBuilder().setName("Charlie").build();
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.CAR).build();
        Rental rental = new RentalBuilder().setId(2222l).setStartDate(date).setEndDate(date).setOwner(userOwner)
                .setClient(userClient).setVehicle(vehicle).build();
        Transaction transaction = new TransactionBuilder().setId(13l).setCost(500).setRental(rental).build();
        Assert.assertTrue(transaction.getState().equals(Transaction.StateTransaction.RESERVATION)
                && transaction.getRental().getState().equals(Rental.RentalState.WAIT_CONFIRM));
        transaction.cancelTransaction();
        Assert.assertTrue(transaction.getState().equals(Transaction.StateTransaction.CANCEL) && transaction.getRental().getState().equals(Rental.RentalState.CANCEL));
    }



    @Test
    public void confirmTransactionChangeStateToConfirmAndRentalStateToInProgressTest(){
        Date date = new Date();
        User userOwner = new UserBuilder().setName("Vladimir").build();
        User userClient = new UserBuilder().setName("Charlie").build();
        Vehicle vehicle = new VehicleBuilder().setVehicleType(Vehicle.VehicleType.MOTORCYCLE).build();
        Rental rental = new RentalBuilder().setId(455l).setStartDate(date).setEndDate(date).setOwner(userOwner)
                .setClient(userClient).setVehicle(vehicle).build();
        Transaction transaction = new TransactionBuilder().setId(13l).setRental(rental).build();
        transaction.setCost(700);
        Assert.assertTrue(transaction.getState().equals(Transaction.StateTransaction.RESERVATION) && transaction.getRental().getState().equals(Rental.RentalState.WAIT_CONFIRM));
        transaction.confirmTransaction();
        Assert.assertTrue(transaction.getState().equals(Transaction.StateTransaction.CONFIRM) && transaction.getRental().getState().equals(Rental.RentalState.IN_PROGRESS));
    }



}
