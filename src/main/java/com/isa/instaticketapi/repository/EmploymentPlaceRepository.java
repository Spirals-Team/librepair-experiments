package com.isa.instaticketapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.EmploymentPlace;
import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.User;

/*
 * 
 * 
 */

@Repository
public interface EmploymentPlaceRepository extends JpaRepository<EmploymentPlace,Long> {

	
	EmploymentPlace findOneById(Long id);
	
	List<User> findAllByPlace(Place place);
	
	List<Place> findAllByAdmin(User user);
	
	
}
