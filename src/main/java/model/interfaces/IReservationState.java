package model.interfaces;

import model.Rental;
import model.Reservation;

public interface IReservationState {
    void beReject(Reservation reservation);
    Rental beConfirm(Reservation reservation);
}
