package model;

import java.util.Date;

/**
 * Created by mariano on 27/03/18.
 */
public class Transaction {


    public enum StateTransaction{
        RESERVATION, REJECTED, CONFIRM, RETIRED, IN_USE, CANCEL, FINALIZED
    }

    private Long id;
    private Integer cost;
    private Date create;
    private Date lastUpdate;
    private StateTransaction state;
    private Rental rental;


    public Transaction(){}


    public Transaction(Long id, Integer cost, Date create, Date lastUpdate, Rental rental){
        this.id = id;
        this.cost = cost;
        this.create = create;
        this.lastUpdate = lastUpdate;
        this.state = StateTransaction.RESERVATION;
        this.rental = rental;
    }


    public Transaction(Integer cost, Rental rental){
        this.state = StateTransaction.RESERVATION;
        this.create = new Date();
        this.lastUpdate = new Date();
        this.cost = cost;
        this.rental = rental;
    }


    ///  init  getters and setters

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }


    public StateTransaction getState() {
        return state;
    }

    public void setState(StateTransaction state) {
        this.state = state;
    }

    public Rental getRental() {
        return rental;
    }



    ///  end  getters and setters


    public void confirmTransaction(){
        this.setState(StateTransaction.CONFIRM);
        this.rental.initRental();
        this.lastUpdate = new Date();
    }


    public void cancelTransaction(){
        this.setState(StateTransaction.CANCEL);
        this.rental.cancelRental();
        this.lastUpdate = new Date();
    }




}
