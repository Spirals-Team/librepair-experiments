package com.isa.instaticketapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.Place;

/**
 * Spring Data JPA repository for the Place entity.
 */
@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
	
	List<Place> findAllByType(String type);
	
	Place findOneById(Long id);
	
	Place findOneByName(String name);
}
