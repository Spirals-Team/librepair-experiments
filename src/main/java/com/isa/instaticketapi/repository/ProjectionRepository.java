package com.isa.instaticketapi.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.Event;
import com.isa.instaticketapi.domain.Hall;
import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.domain.Repertory;

/**
 * Spring Data JPA repository for the Projection entity.
 */
@Repository
public interface ProjectionRepository extends JpaRepository<Projection, Long> {
	ArrayList<Projection> findAllByHall(Hall hall);

	ArrayList<Projection> findAll();

	ArrayList<Projection> findAllByReperotry(Repertory repertory);

	Projection findOneById(Long id);

	ArrayList<Projection> findAllByEvent(Event event);

}
