package model.builder;

import model.Rental;
import model.Transaction;

import java.util.Date;

/**
 * Created by mariano on 08/04/18.
 */
public class TransactionBuilder {

    public enum StateTransaction{
        RESERVATION, REJECTED, CONFIRM, RETIRED, IN_USE, CANCEL, FINALIZED
    }

    private Long id;
    private Integer cost;
    private Date create;
    private Date lastUpdate;
    private Transaction.StateTransaction state;
    private Rental rental;


    public TransactionBuilder(){}


    public TransactionBuilder(Long id, Integer cost, Date create, Date lastUpdate, Rental rental){
        this.id = id;
        this.cost = cost;
        this.create = create;
        this.lastUpdate = lastUpdate;
        this.state = Transaction.StateTransaction.RESERVATION;
        this.rental = rental;
    }


    public TransactionBuilder(Integer cost, Rental rental){
        this.state = Transaction.StateTransaction.RESERVATION;
        this.cost = cost;
        this.rental = rental;
        this.create = new Date();
        this.lastUpdate = new Date();
    }


    public TransactionBuilder setId(Long id){
        this.id = id;
        return this;
    }


    public TransactionBuilder setCreate(Date create){
        this.create = create;
        return this;
    }


    public TransactionBuilder setLastUpdate(Date lastUpdate){
        this.lastUpdate = lastUpdate;
        return this;
    }


    public TransactionBuilder setCost(Integer cost){
        this.cost = cost;
        return this;
    }

    public TransactionBuilder setRental(Rental rental){
        this.rental = rental;
        return this;
    }



    public Transaction build(){
        return new Transaction(this.id, this.cost, this.create, this.lastUpdate, this.rental);
    }


}
