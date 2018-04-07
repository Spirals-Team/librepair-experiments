package model.states.rental;

import model.Rental;
import model.exceptions.InvalidStatusChangeException;
import model.interfaces.IRentalState;

public class ConfirmedByTheTenantST implements IRentalState {

    public void ownerUserConfirmated(Rental rental) {
        rental.startRentalTime();
        rental.setState(new PendingReturnRentalST());
    }

    public void tenantUserConfirmated(Rental rental) {
        throw new InvalidStatusChangeException("Ya contamos con la confirmación del inquilino");
    }

    public void ownerUserConfirmated(Rental rental, Integer score, String comment) {
        throw new InvalidStatusChangeException("Estado inválido");
    }

    public void tenantUserConfirmated(Rental rental, Integer score, String comment) {
        throw new InvalidStatusChangeException("Estado inválido");
    }
}
