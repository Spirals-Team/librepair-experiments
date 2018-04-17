package com.isa.instaticketapi.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.Repertory;

/**
 * Spring Data JPA repository for the Repertory entity.
 */
@Repository
public interface RepertotyRepository extends JpaRepository<Repertory, Long> {
	ArrayList<Repertory> findALLByDate(String date);
	ArrayList<Repertory> findAllByPlace(Place place);
	Repertory findOneById(Long id);

}
