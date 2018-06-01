package com.m2dl.dlmovie.movies.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "movies")
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

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public String getDirector() {
        return director;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return id != null ? id.equals(movie.id) : movie.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
