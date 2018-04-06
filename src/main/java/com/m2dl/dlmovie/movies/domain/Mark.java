package com.m2dl.dlmovie.movies.domain;

import com.m2dl.dlmovie.users.domain.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Mark {

    @Id
    @GeneratedValue
    private Long id;

    @Min(1)
    @Max(5)
    private int value;

    @NotNull
    private Date date;

    @ManyToOne
    private User user;

    /*@ManyToOne
    private Movie movie;*/

    public Mark(){}

    public Mark(int value, Date date) {
        this.value = value;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }*/
}
