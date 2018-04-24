package model.builder;

import model.Rental;
import model.User;
import model.Vehicle;

import java.util.Date;

/**
 * Created by mariano on 08/04/18.
 */
public class RentalBuilder {

    public enum RentalState{
        IN_PROGRESS, WAIT_CONFIRM, DONE, CANCEL
    }

    private Long id;
    private Date startDate;
    private Date endDate;
    private User owner;
    private User client;
    private Vehicle vehicle;
    private Rental.RentalState state;



    public RentalBuilder(){}


    public RentalBuilder(User owner, User client, Vehicle vehicle){
        this.state = Rental.RentalState.WAIT_CONFIRM;
        this.owner = owner;
        this.client = client;
        this.vehicle = vehicle;
    }



    public RentalBuilder(Long id, Date startDate, Date endDate, User owner, User client, Vehicle vehicle){
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.owner = owner;
        this.client = client;
        this.vehicle = vehicle;
        this.state = Rental.RentalState.WAIT_CONFIRM;
    }



    public RentalBuilder setId(Long id){
        this.id = id;
        return this;
    }


    public RentalBuilder setStartDate(Date startDate){
        this.startDate = startDate;
        return this;
    }


    public RentalBuilder setEndDate(Date endDate){
        this.endDate = endDate;
        return this;
    }

    public RentalBuilder setOwner(User owner){
        this.owner = owner;
        return this;
    }


    public RentalBuilder setClient(User client){
        this.client = client;
        return this;
    }

    public RentalBuilder setVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
        return this;
    }



    public Rental build(){
        return new Rental(this.id, this.startDate, this.endDate, this.owner, this.client, this.vehicle);
    }


}
