package model;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Created by mariano on 27/03/18.
 */
@Entity
@Table
public class Vehicle {


    public enum VehicleType{
        MOTORCYCLE, CAR, VAN, TRUCK
    }


    private VehicleType type;
    private Integer capacity;
    private String location;
    private String retirementAddress;   //  MAPA
    private String returnAddress;   //  MAPA  -  List
    private String description;
    private String phone;
    private Integer cost;
    private String planilla;  // horario y dias disponibles para alquilar
    // private String fotos   escuche que deberia ser un String que sea el link a la imagen



    public Vehicle(){}

    public Vehicle(VehicleType type){
        this.type = type;
    }

    public Vehicle(VehicleType type, Integer capacity, String location, String retirementAddress,
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



    public VehicleType getType() {
        return type;
    }

    public String getTypeName(){
        return type.name();
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRetirementAddress() {
        return retirementAddress;
    }

    public void setRetirementAddress(String retirementAddress) {
        this.retirementAddress = retirementAddress;
    }

    public String getReturnAddress() {
        return returnAddress;
    }

    public void setReturnAddress(String returnAddress) {
        this.returnAddress = returnAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlanilla() {
        return planilla;
    }

    public Integer getCost() {
        return cost;
    }

    public void revalueateCost(Integer newcost){
        this.setCost(newcost);
    }



    public Boolean isValidDescription(String name){
        return (name.length() >= 30) && (name.length() <= 200);
    }


    public Boolean isValidPhoneNumber(String phoneNumber){
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        PhoneNumber phone = new PhoneNumber();
        try {
            phone = phoneUtil.parse(phoneNumber, "ES");
        } catch (NumberParseException e) {
            System.err.println("InvalidPhoneNumberException was thrown: " + e.toString());
        }
        //phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        return phoneUtil.isValidNumber(phone);
    }



    public void setCost(Integer cost) {
        this.cost = cost;
    }

}
