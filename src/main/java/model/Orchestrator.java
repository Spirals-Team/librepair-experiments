package model;

import model.builder.RentalBuilder;
import model.builder.UserBuilder;
import model.builder.VehicleBuilder;

import java.util.*;

/**
 * Created by mariano on 03/04/18.
 */
public class Orchestrator {

    Map<String,User> userSystem;
    Map<Integer,Transaction> transactionsSystem;
    MailSender sendMail;
    Map<Long,Rental> rentalSystem;



    public Orchestrator(){
        this.userSystem=new HashMap<String, User>();
        this.transactionsSystem= new HashMap<Integer,Transaction>();
        this.sendMail= new MailSender();
        this.rentalSystem=new HashMap<Long,Rental>();
    }

    public Map<String, User> getUserSystem() {
        return userSystem;
    }

    public void setUserSystem(Map<String, User> userSystem) {
        this.userSystem = userSystem;
    }

    public Map<Integer, Transaction> getTransactionsSystem() {
        return transactionsSystem;
    }

    public void setTransactionsSystem(Map<Integer, Transaction> transactionsSystem) {
        this.transactionsSystem = transactionsSystem;
    }

    public MailSender getSendMail() {
        return sendMail;
    }

    public void setSendMail(MailSender sendMail) {
        this.sendMail = sendMail;
    }

    public Map<Long, Rental> getRentalSystem() {
        return rentalSystem;
    }

    public void setRentalSystem(Map<Long, Rental> rentalSystem) {
        this.rentalSystem = rentalSystem;
    }

    protected User getUser(String cuil){
        return userSystem.get(cuil);
    }
    protected void addUser(String cuil, String name, String surname, String address, String email){
      userSystem.put(cuil,new UserBuilder(cuil,name,surname,address,email).build());

    }
    protected void addVehicle(String userCuil, Vehicle.VehicleType type, Integer capacity, String location, String retirementAddress, String returnAddress, String description, String phone, Integer cost){
        userSystem.get(userCuil).addVehicle(new VehicleBuilder(type,capacity,location,retirementAddress,returnAddress,description,phone,cost).build());

    }
    protected void generateRental(String ownerCuil, String clientCuil,Vehicle vehicle,Long id ){
        User client = this.getUser(clientCuil);
        User owner = this.getUser(ownerCuil);


        this.rentalSystem.put(id,new RentalBuilder().setId(id).setVehicle(vehicle)
                                                    .setClient(client)
                                                    .setOwner(owner).build()
                                                    );


    }

}
