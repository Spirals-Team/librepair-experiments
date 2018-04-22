package model.states.rental;


import model.Rental;
import model.exceptions.InvalidStatusChangeException;
import model.interfaces.IRentalState;

public class ConfirmedByTheOwnerST implements IRentalState {

    public void ownerUserConfirmated(Rental rental) {
        throw new InvalidStatusChangeException("Ya contamos con la confirmación del dueño");
    }


    public void tenantUserConfirmated(Rental rental) {
        rental.startRentalTime();
        rental.setState(new PendingReturnRentalST());
    }

    public void ownerUserConfirmated(Rental rental, Integer score, String comment) {
        throw new InvalidStatusChangeException("Estado inválido");
    }

    public void tenantUserConfirmated(Rental rental, Integer score, String comment) {
        throw new InvalidStatusChangeException("Estado inválido");
    }
}
