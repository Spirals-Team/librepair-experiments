package com.lala.app;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.lala.app.domain.Movie;
import com.lala.app.domain.Ticket;
import com.lala.app.repository.TicketManager;

@Ignore

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@Rollback
//@Commit
@Transactional(transactionManager = "txManager")
public class TicketManagerTest {

	@Autowired
	TicketManager ticketManager;

	private final String TITLE_1 = "Nietykalni";
    private final int YEAR_1 = 2011;
    private final String GENRE_1 = "Dramat";
	private final String DIRECTOR_1 = "Ketch";

	private final String TITLE_2 = "Siedem";
    private final int YEAR_2 = 1997;
    private final String GENRE_2 = "Thriller";
	private final String DIRECTOR_2 = "Ketch";

	private final String TYPE_1 = "Normal";
	private final int PRICE_1 = 40;

	private final String TYPE_2 = "Discount";
	private final int PRICE_2 = 20;

	@Test
	public void addMovieCheck() {

		List<Movie> retrievedMovies= ticketManager.getAllMovies();
		// If there is a client with PIN_1 delete it
		for (Movie movie : retrievedMovies) {
			System.out.println("Film: " + movie.getTitle() + " " + movie.getYear() + " " + movie.getGenre()+ " " + movie.getDirector());
			if (movie.getTitle().equals(TITLE_1)) {
				ticketManager.deleteMovie(movie);
			}
		}
		retrievedMovies = ticketManager.getAllMovies();

		Movie movie = new Movie();
		movie.setTitle(TITLE_1);
        movie.setYear(YEAR_1);
        movie.setGenre(GENRE_1);
		movie.setDirector(DIRECTOR_1);
		// ... other properties here

		// Pin is Unique
		ticketManager.addMovie(movie);

		Movie retrievedMovie = ticketManager.findMovieByTitle(TITLE_1);

		assertEquals(TITLE_1, retrievedMovie.getTitle());
		assertEquals(YEAR_1, retrievedMovie.getYear());
		// ... check other properties here

	}

	@Test
	public void addTicketCheck() {

		Ticket ticket = new Ticket();
		ticket.setType(TYPE_1);
		ticket.setPrice(PRICE_1);
		// ... other properties here

		long ticketId = ticketManager.addNewTicket(ticket);

		Ticket retrievedTicket = ticketManager.findTicketById(ticketId);
		assertEquals(TYPE_1, retrievedTicket.getType());
		assertEquals(PRICE_1, retrievedTicket.getPrice());
		// ... check other properties here

	}

	@Test
	public void sellTicketCheck() {

		Movie movie = new Movie();
		movie.setTitle(TITLE_2);
        movie.setYear(YEAR_2);
        movie.setGenre(GENRE_2);
		movie.setDirector(DIRECTOR_2);
		ticketManager.addMovie(movie);

		Movie retrievedMovie = ticketManager.findMovieByTitle(TITLE_2);

		Ticket ticket = new Ticket();
		ticket.setType(TYPE_2);
		ticket.setPrice(PRICE_2);

		Long ticketId = ticketManager.addNewTicket(ticket);

		ticketManager.sellTicket(retrievedMovie.getId(), ticketId);

		List<Ticket> ownedTickets = ticketManager.getOwnedTicket(retrievedMovie);

		assertEquals(1, ownedTickets.size());
		assertEquals(TYPE_2, ownedTickets.get(0).getType());
		assertEquals(PRICE_2, ownedTickets.get(0).getPrice());
	}

	// @Test -
	public void disposeTicketCheck() {
		// Do it yourself
	}

}