package com.lala.app.repository;

import java.util.List;

import com.lala.app.domain.Movie;
import com.lala.app.domain.Ticket;

public interface TicketManager {
	
	void addMovie(Movie movie);
	List<Movie> getAllMovies();
	void deleteMovie(Movie movie);
	Movie findMovieByTitle(String title);
	
	Long addNewTicket(Ticket ticket);
	List<Ticket> getAvailableTickets();
	void disposeTicket(Movie movie, Ticket ticket);
	Ticket findTicketById(Long id);

	List<Ticket> getOwnedTicket(Movie movie);
	void sellTicket(Long movieId, Long ticketId);

    void updateMovie(Movie movie);
}