package com.m2dl.dlmovie.movies.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue
    Long id;

    @NotEmpty
    private String title;

    private String description;

    @NotNull
    private Date date;

    @NotEmpty
    @Length(min = 2)
    private String director;

    @OneToMany
    private List<Mark> marks;

    @OneToMany
    private List<Comment> comments;

    public Movie() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Movie(String title, String description, Date date, String director) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.director = director;
        this.marks = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

}
