package model.states.rental;


import model.AccountManager;
import model.Rental;
import model.Review;
import model.exceptions.InvalidStatusChangeException;
import model.interfaces.IRentalState;

import java.time.LocalDateTime;

public class ReturnConfirmedByTheOwner implements IRentalState {

    public void ownerUserConfirmated(Rental rental) {
        throw new InvalidStatusChangeException("Estado inválido");
    }

    public void tenantUserConfirmated(Rental rental) {
        throw new InvalidStatusChangeException("Estado inválido");
    }

    public void ownerUserConfirmated(Rental rental, Integer score, String comment) {
        throw new InvalidStatusChangeException("Ya contamos con la confirmación del dueño");
    }

    public void tenantUserConfirmated(Rental rental, Integer score, String comment) {
        Review review = new Review(score, comment, rental.getOwnerUser());
        rental.getOwnerUser().processScore(score);
        LocalDateTime endRentalTime = LocalDateTime.now();
        AccountManager.processPayment(rental.rentCost(endRentalTime),
                rental.getTenantUser(), rental.getOwnerUser());
        rental.setState(new FinalizedRentalST());
    }
}
