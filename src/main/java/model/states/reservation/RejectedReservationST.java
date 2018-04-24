package model.states.reservation;

import model.Rental;
import model.Reservation;
import model.exceptions.InvalidStatusChangeException;
import model.interfaces.IReservationState;


public class RejectedReservationST implements IReservationState {

    @Override
    public void beReject(Reservation reservation) {
        throw new InvalidStatusChangeException
                ("No podes cambiar el estado de la reserva, de Rechazada a Rechazada");
    }

    @Override
    public Rental beConfirm(Reservation reservation) {
        throw new InvalidStatusChangeException
                ("No podes cambiar el estado de la reserva, de Rechazada a Confirmada");
    }
}
