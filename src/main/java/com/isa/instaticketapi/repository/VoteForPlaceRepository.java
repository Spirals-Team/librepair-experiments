package com.isa.instaticketapi.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.VoteForPlace;

/**
 * Spring Data JPA repository for the vote for place entity.
 */
@Repository
public interface VoteForPlaceRepository extends JpaRepository<VoteForPlace, Long>{
	ArrayList<VoteForPlace> findAllByPlace(Place place);
}
