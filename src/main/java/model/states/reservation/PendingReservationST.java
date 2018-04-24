package model.states.reservation;


import model.Rental;
import model.Reservation;
import model.interfaces.IReservationState;

public class PendingReservationST implements IReservationState {

    public void beReject(Reservation reservation) {
        reservation.setStatus(new RejectedReservationST());
    }

    @Override
    public Rental beConfirm(Reservation reservation) {
        reservation.setStatus(new ConfirmReservationST());
        return new Rental(reservation);
    }

}
