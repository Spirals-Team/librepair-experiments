package com.isa.instaticketapi.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Ticket")
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    private Reservation reservation;

    @Column(name = "ticket_type")
    private String tickeyType;

    @OneToOne
    private Seat seat;

    public Ticket() {

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

    public String getTickeyType() {
        return tickeyType;
    }

    public void setTickeyType(String tickeyType) {
        this.tickeyType = tickeyType;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
