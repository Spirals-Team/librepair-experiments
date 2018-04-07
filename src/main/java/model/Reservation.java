package model;


import model.interfaces.IReservationState;
import model.states.reservation.PendingReservationST;

import java.time.LocalDateTime;

public class Reservation {

    private User tenantUser;
    private Post post;
    private LocalDateTime reservationSinceDate;
    private LocalDateTime reservationUntilDate;
    private IReservationState statusReservation;

    public Reservation(Post post, LocalDateTime reservationSinceDate,
                       LocalDateTime reservationUntilDate, User tenantUser){
        this.tenantUser=tenantUser;
        this.post=post;
        this.reservationSinceDate=reservationSinceDate;
        this.reservationUntilDate=reservationUntilDate;

        this.statusReservation = new PendingReservationST();

    }

    public void setStatus(IReservationState status){
        this.statusReservation=status;
    }

    public IReservationState getStatus(){
        return this.statusReservation;
    }

    public void beReject(){
        this.statusReservation.beReject(this);
    }

    public Rental beConfirm(){
        return this.statusReservation.beConfirm(this);
    }

    public User getTenantUser(){
        return this.tenantUser;
    }

    public User getOwnerUser(){
        return this.post.getUser();
    }

    public Post getPost() {
        return post;
    }
}
