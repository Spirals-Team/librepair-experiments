package model.builder;

import model.Vehicle;

/**
 * Created by mariano on 01/04/18.
 */
public class VehicleBuilder {

    private Vehicle.VehicleType type;
    private Integer capacity;
    private String location;
    private String retirementAddress;
    private String returnAddress;
    private String description;
    private String phone;
    private Integer cost;


public VehicleBuilder(){}

    public VehicleBuilder(Vehicle.VehicleType type){
        this.type = type;
    }

    public VehicleBuilder(Vehicle.VehicleType type, Integer capacity, String location, String retirementAddress,
                   String returnAddress, String description, String phone, Integer cost){
        this.type = type;
        this.capacity = capacity;
        this.location = location;
        this.retirementAddress = retirementAddress;
        this.returnAddress = returnAddress;
        this.description = description;
        this.phone = phone;
        this.cost = cost;
    }


    public VehicleBuilder setVehicleType(Vehicle.VehicleType type){
        this.type = type;
        return this;
    }


    public VehicleBuilder setCapacity(Integer capacity){
        this.capacity = capacity;
        return this;
    }

    public VehicleBuilder setLocation(String location){
        this.location = location;
        return this;
    }


    public VehicleBuilder setRetirementAddress(String retirementAddress){
        this.retirementAddress = retirementAddress;
        return this;
    }


    public VehicleBuilder setReturnAddress(String returnAddress){
        this.returnAddress = returnAddress;
        return this;
    }


    public VehicleBuilder setDescription(String description){
        this.description = description;
        return this;
    }


    public VehicleBuilder setPhone(String phone){
        this.phone = phone;
        return this;
    }


    public VehicleBuilder setCost(Integer cost){
        this.cost = cost;
        return this;
    }


    public Vehicle build(){
        return new Vehicle(this.type, this.capacity, this.location, this.retirementAddress, this.returnAddress, this.description, this.phone, this.cost);
    }



}
