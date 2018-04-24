package model.states.rental;


import model.Rental;
import model.exceptions.InvalidStatusChangeException;
import model.interfaces.IRentalState;

public class FinalizedRentalST implements IRentalState {
    public void ownerUserConfirmated(Rental rental) {
        throw new InvalidStatusChangeException("El alquiler ya se encuentra finalizado");
    }

    public void tenantUserConfirmated(Rental rental) {
        throw new InvalidStatusChangeException("El alquiler ya se encuentra finalizado");
    }

    public void ownerUserConfirmated(Rental rental, Integer score, String comment) {
        throw new InvalidStatusChangeException("El alquiler ya se encuentra finalizado");
    }

    public void tenantUserConfirmated(Rental rental, Integer score, String comment) {
        throw new InvalidStatusChangeException("El alquiler ya se encuentra finalizado");
    }
}
