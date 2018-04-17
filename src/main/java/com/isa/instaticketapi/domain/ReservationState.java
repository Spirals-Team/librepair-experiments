package com.isa.instaticketapi.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Reservation_state")
public class ReservationState implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Reservation reservation;

    @ManyToOne
    private User userIncludedInReservation;

    @Column(name = "drop_out")
    private boolean dropOut;

    @Column(name = "been_there")
    private boolean isUsed;

    public ReservationState() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public User getUserIncludedInReservation() {
        return userIncludedInReservation;
    }

    public void setUserIncludedInReservation(User userIncludedInReservation) {
        this.userIncludedInReservation = userIncludedInReservation;
    }

    public boolean isDropOut() {
        return dropOut;
    }

    public void setDropOut(boolean dropOut) {
        this.dropOut = dropOut;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
