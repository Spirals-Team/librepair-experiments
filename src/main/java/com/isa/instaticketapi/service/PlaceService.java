package com.isa.instaticketapi.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.config.Constants;
import com.isa.instaticketapi.domain.Event;
import com.isa.instaticketapi.domain.Hall;
import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.domain.Repertory;
import com.isa.instaticketapi.domain.Reservation;
import com.isa.instaticketapi.domain.ReservationState;
import com.isa.instaticketapi.domain.Seat;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.domain.VoteForPlace;
import com.isa.instaticketapi.repository.EventRepository;
import com.isa.instaticketapi.repository.HallRepository;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.ProjectionRepository;
import com.isa.instaticketapi.repository.RepertotyRepository;
import com.isa.instaticketapi.repository.ReservationRepository;
import com.isa.instaticketapi.repository.ReservationStateRepository;
import com.isa.instaticketapi.repository.SeatRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.repository.VoteForPlaceRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.places.PlaceDTO;

/**
 * Service for managing cinema and theatres
 * 
 * @author Milica Kovacevic
 *
 */
@Service
@Transactional
public class PlaceService {
	private final Logger log = LoggerFactory.getLogger(PlaceService.class);

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private HallRepository hallRepository;

	@Autowired
	private ProjectionRepository projectionRepository;

	@Autowired
	private RepertotyRepository repertoryRepository;

	@Autowired
	private VoteForPlaceRepository voteForPlaceRepository;

	@Autowired
	private SeatRepository seatRepository;
	
	@Autowired
	private ReservationRepository reservationrepository;
	
	@Autowired
	private ReservationStateRepository reservationStaterepository;

	/**
	 * 
	 * @return list of all cinemas
	 */
	public List<Place> getCinemas() {
		return placeRepository.findAllByType(Constants.CINEMA);
	}

	/**
	 * 
	 * @return list of all theaters
	 */
	public List<Place> getTheaters() {
		return placeRepository.findAllByType(Constants.THEATER);
	}

	/**
	 * 
	 * @param id
	 * @return cinema or theater
	 */
	public Place getPlace(Long id) {
		return placeRepository.findOneById(id);
	}

	/**
	 * 
	 * @param changePlaceDTO
	 *            object for editing
	 * @param id
	 *            id of object
	 */

	public Place changePlace(PlaceDTO placeDTO, long id) {
		Place place = placeRepository.findOneById(id);
		if (place == null) {
			return null;
		}

		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		place.setLastModifiedBy(logged.getUsername());
		place.setName(placeDTO.getName());
		place.setAddress(placeDTO.getAddress());
		place.setDescripton(placeDTO.getDescription());
		place.setType(placeDTO.getType());
		placeRepository.save(place);
		return place;

	}

	public void createPlace(PlaceDTO placeDTO) throws SQLException {

		Place place = new Place();

		place.setName(placeDTO.getName());
		place.setAddress(placeDTO.getAddress());
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		place.setCreatedBy(logged.getUsername());
		place.setDescripton(placeDTO.getDescription());
		place.setType(placeDTO.getType());

		placeRepository.save(place); // maybe error - unique name

	}

	/**
	 * Deleting place from database
	 * 
	 * @param id
	 *            representing id of place which will be deleted
	 */
	public Place deletePlace(Long id) {
		Place place = placeRepository.findOneById(id);
		if (place == null) {
			return null;
		}
		ArrayList<Hall> halls = hallRepository.findAllByPlace(place);
		for (int i = 0; i < halls.size(); i++) {
			ArrayList<Projection> projections = projectionRepository.findAllByHall(halls.get(i));
			for (int j = 0; j < projections.size(); j++) {
				projectionRepository.delete(projections.get(j));
			}
			hallRepository.delete(halls.get(i));
		}
		ArrayList<Repertory> reprtories = repertoryRepository.findAllByPlace(place);
		repertoryRepository.delete(reprtories);

		ArrayList<Event> events = eventRepository.findAllByPlace(place);
		eventRepository.delete(events);

		placeRepository.delete(place);
		log.debug("Deleted place.");
		return place;

	}

	/**
	 * 
	 * @param id
	 *            id of place
	 * @return list of event in place
	 */
	public ArrayList<Event> getEventsInPlace(Long id) {

		Place place = placeRepository.findOneById(id);
		if (place == null) {
			throw new IllegalArgumentException("Invalid id!");
		}

		ArrayList<Event> events = eventRepository.findAllByPlace(place);
		return events;
	}

	/**
	 * 
	 * @param id
	 *            id of place
	 * @return list of repertory(objects)
	 */
	public ArrayList<Repertory> getRepertoriesInPlace(Long id) {
		Place place = placeRepository.findOneById(id);
		return repertoryRepository.findAllByPlace(place);
	}

	/**
	 * 
	 * @param id
	 *            id of place
	 * @return vote for place
	 */
	public int getVoteForPlace(Long id) {

		int voteSum = 0;
		int vote = 0;
		ArrayList<VoteForPlace> votes = voteForPlaceRepository.findAllByPlace(placeRepository.findOneById(id));
		for (int i = 0; i < votes.size(); i++) {
			voteSum += votes.get(i).getVote();
		}
		if (votes.isEmpty()) {
			return vote;
		}
		vote = voteSum / votes.size();
		return vote;
	}

	/**
	 * 
	 * @param id id of place
	 * @return list of seat object
	 */
	public ArrayList<Seat> getQuickSeats(Long id) {
		Place place = placeRepository.findOneById(id);
		ArrayList<Hall> halls = hallRepository.findAllByPlace(place);

		ArrayList<Seat> seats = new ArrayList<Seat>();
		for (int i = 0; i < halls.size(); i++) {
			ArrayList<Seat> allSeat = seatRepository.findAllByHall(halls.get(i));
			for (int j = 0; j < allSeat.size(); j++) {
				if ((allSeat.get(j).getSeatType()).equals("Brza rezervacija") && !allSeat.get(j).isReserved()
						&& allSeat.get(j).isSeat()) {
					seats.add(allSeat.get(j));
				}
			}
		}

		return seats;
	}

	/**
	 * 
	 * @param id
	 *            of seat for reservation
	 */
	public void reservation(Long id) {
		Seat seat = seatRepository.findOneById(id);
		seat.setReserved(true);
		
		Reservation reservation = new Reservation();
		reservation.setProjection(seat.getProjection());
		reservationrepository.save(reservation);
		
		ReservationState reservationState= new ReservationState();
		reservationState.setReservation(reservation);
		reservationStaterepository.save(reservationState);
		
	}
}
