package model;

import java.util.Date;

/**
 * Created by mariano on 03/04/18.
 */
public class Rental {

    public enum RentalState{
        IN_PROGRESS, WAIT_CONFIRM, DONE, CANCEL
    }

    private Long id;
    private Date startDate;
    private Date endDate;
    private User owner;
    private User client;
    private Vehicle vehicle;
    private RentalState state;


    public Rental(){}


    public Rental(Long id, Date startDate, Date endDate, User owner, User client, Vehicle vehicle){
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = RentalState.WAIT_CONFIRM;
        this.owner = owner;
        this.client = client;
        this.vehicle = vehicle;
    }


    public Rental(User owner, User client, Vehicle vehicle){
        this.state = RentalState.WAIT_CONFIRM;
        this.owner = owner;
        this.client = client;
        this.vehicle = vehicle;
    }


///  init  getters and setters

    public Date getStartDate() {
        return startDate;
    }



    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getOwner() {
        return owner;
    }



    public User getClient() {
        return client;
    }



    public Vehicle getVehicle() {
        return vehicle;
    }



    public RentalState getState() {
        return state;
    }

    public void setState(RentalState state) {
        this.state = state;
    }

    ///  end  getters and setters



    public void initRental(){
        this.setState(RentalState.IN_PROGRESS);
    }


    public void cancelRental(){
        this.setState(RentalState.CANCEL);

    }


}
