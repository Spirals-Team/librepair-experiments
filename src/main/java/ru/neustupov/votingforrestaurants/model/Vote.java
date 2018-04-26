package ru.neustupov.votingforrestaurants.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "votes")
public class Vote extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @Column(name = "date", columnDefinition = "date default current_date",  nullable = false)
    @NotNull
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(Vote v) {
        this(v.getId(), v.getDate());
    }

    public Vote(@NotNull Date date) {
        this.date = date;
    }

    public Vote(@NotNull User user, @NotNull Date date) {
        this.user = user;
        this.date = date;
    }

    public Vote(@NotNull Integer id, @NotNull Date date) {
        super(id);
        this.date = date;
    }

    public Vote(@NotNull Integer id, @NotNull User user, @NotNull Date date) {
        super(id);
        this.user = user;
        this.date = date;
    }

    public Vote(@NotNull User user, @NotNull Date date, @NotNull Restaurant restaurant) {
        this.user = user;
        this.date = date;
        this.restaurant = restaurant;
    }

    public Vote(@NotNull Integer id, @NotNull User user, @NotNull Date date, @NotNull Restaurant restaurant) {
        super(id);
        this.user = user;
        this.date = date;
        this.restaurant = restaurant;
    }

    public Vote(@NotNull Date date, @NotNull Restaurant restaurant){
        this.date = date;
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date dateTime) {
        this.date = dateTime;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
