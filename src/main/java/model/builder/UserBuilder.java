package model.builder;

import model.CurrentAccount;
import model.Score;
import model.User;
import model.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariano on 01/04/18.
 */
public class UserBuilder {

    private String cuil;
    private String name;
    private String surname;
    private String address;
    private String email;
    private CurrentAccount currentAccount;

    private List<Score> puntuations = new ArrayList<Score>();

    private List<Vehicle> vehicles = new ArrayList<Vehicle>();

    public UserBuilder(){}

    public UserBuilder(String cuil, String name, String surname, String address, String email,
                       CurrentAccount currentAccount, List<Score> puntuations, List<Vehicle> vehicles){
        this.cuil = cuil;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.currentAccount = currentAccount;
        this.puntuations = puntuations;
        this.vehicles = vehicles;
    }

    public UserBuilder(String cuil, String name, String surname, String address, String email){
        this.cuil = cuil;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.currentAccount = new CurrentAccount(cuil);
    }

    public UserBuilder setCuil(String cuil){
        this.cuil = cuil;
        return this;
    }


    public UserBuilder setName(String name){
        this.name = name;
        return this;
    }


    public UserBuilder setSurname(String surname){
        this.surname = surname;
        return this;
    }

    public UserBuilder setAddress(String address){
        this.address = address;
        return this;
    }

    public UserBuilder setEmail(String email){
        this.email = email;
        return this;
    }

    public UserBuilder setCurrentAccount(CurrentAccount currentAccount){
        this.currentAccount = currentAccount;
        return this;
    }

    public UserBuilder setPuntuations(List<Score> puntuations){
        this.puntuations = puntuations;
        return this;
    }


    public UserBuilder setVehicles(List<Vehicle> vehicles){
        this.vehicles = vehicles;
        return this;
    }


    public User build(){
        return new User(this.cuil, this.name, this.surname, this.address, this.email, this.currentAccount, this.puntuations, this.vehicles);
    }


}
