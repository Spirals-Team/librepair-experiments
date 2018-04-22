package model.interfaces;


import model.Rental;

public interface IRentalState {

    void ownerUserConfirmated(Rental rental);
    void tenantUserConfirmated(Rental rental);

    void ownerUserConfirmated(Rental rental, Integer score, String comment);
    void tenantUserConfirmated(Rental rental, Integer score, String comment);

}
