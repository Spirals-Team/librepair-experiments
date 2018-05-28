package com.lala.app.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQueries({ 
	@NamedQuery(name = "movie.all", query = "Select m from Movie m"),
	@NamedQuery(name = "movie.byTitle", query = "Select m from Movie m where m.title = :title")
})


public class Movie {
    public Long  id;
    public String title;
    public int year;
    public String genre;
    public String director;
    private List<Ticket> tickets;
    

    public Movie() {
    }

    public Movie(Long id, String title, int year, String genre, String director) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.director = director;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long  getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(unique = true, nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public boolean equals(Object o) {
        Movie other = (Movie) o;
        boolean ret = other.getTitle().equals(this.getTitle()) && (other.getId() == this.getId())
                && (other.getYear() == this.getYear()) && (other.getGenre() == this.getGenre())
                && (other.getDirector() == this.getDirector());
        return ret;
    }

    @Override
    public String toString() {
        return "[" + id + ", " + title + ", " + year + ", " + genre + ", " + director + "]";
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@OneToMany(orphanRemoval=true)
    //@JoinColumn(name="OWNER")
	public List<Ticket> getTicket() {
		return tickets;
	}
	public void setTicket(List<Ticket> tickets) {
		this.tickets = tickets;
}


}