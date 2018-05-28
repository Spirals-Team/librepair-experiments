package com.lala.app.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lala.app.domain.Movie;
import com.lala.app.domain.Ticket;

@Component
@Transactional
public class TicketMangerHibernateImpl implements TicketManager {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void addMovie(Movie movie) {
		movie.setId(null);
		sessionFactory.getCurrentSession().persist(movie);
		sessionFactory.getCurrentSession().flush();
	}
    @Override
    public void updateMovie(Movie movie) {
        sessionFactory.getCurrentSession().update(movie);
    }

	@Override
	public void deleteMovie(Movie movie) {
		movie = (Movie) sessionFactory.getCurrentSession().get(Movie.class,
        movie.getId());
		
		// lazy loading here
		for (Ticket ticket : movie.getTicket()) {
			ticket.setSold(false);
			sessionFactory.getCurrentSession().update(ticket);
		}
		//person.getCars().clear();
        //sessionFactory.getCurrentSession().update(person);

		sessionFactory.getCurrentSession().delete(movie);
	}

	@Override
	public List<Ticket> getOwnedTicket(Movie movie) {
		movie = (Movie) sessionFactory.getCurrentSession().get(Movie.class,
				movie.getId());
		// lazy loading here - try this code without (shallow) copying
		List<Ticket> tickets = new ArrayList<Ticket>(movie.getTicket());
		return tickets;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Movie> getAllMovies() {
		return sessionFactory.getCurrentSession().getNamedQuery("movie.all")
				.list();
	}

	@Override
	public Movie findMovieByTitle(String title) {
		return (Movie) sessionFactory.getCurrentSession().getNamedQuery("movie.byTitle").setString("title", title).uniqueResult();
	}


	@Override
    public Long addNewTicket(Ticket ticket) {
		ticket.setId(null);
		return (Long) sessionFactory.getCurrentSession().save(ticket);
	}

	@Override
	public void sellTicket(Long movieId, Long ticketId) {
		Movie movie = (Movie) sessionFactory.getCurrentSession().get(
				Movie.class, movieId);
		Ticket ticket = (Ticket) sessionFactory.getCurrentSession()
				.get(Ticket.class, ticketId);
		ticket.setSold(true);
		if (movie.getTicket() == null) {
			movie.setTicket(new LinkedList<Ticket>());
		}
		movie.getTicket().add(ticket);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Ticket> getAvailableTickets() {
		return sessionFactory.getCurrentSession().getNamedQuery("ticket.unsold")
				.list();
	}
	@Override
	public void disposeTicket(Movie movie, Ticket ticket) {

		movie = (Movie) sessionFactory.getCurrentSession().get(Movie.class,
				movie.getId());
		ticket = (Ticket) sessionFactory.getCurrentSession().get(Ticket.class,
				ticket.getId());

		Ticket toRemove = null;
		// lazy loading here (person.getCars)
		for (Ticket aTicket : movie.getTicket())
			if (aTicket.getId().compareTo(ticket.getId()) == 0) {
				toRemove = aTicket;
				break;
			}

		if (toRemove != null)
			movie.getTicket().remove(toRemove);

            ticket.setSold(false);
	}

	@Override
	public Ticket findTicketById(Long id) {
		return (Ticket) sessionFactory.getCurrentSession().get(Ticket.class, id);
	}

}