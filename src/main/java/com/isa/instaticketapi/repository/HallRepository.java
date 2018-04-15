package com.isa.instaticketapi.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.Hall;
import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.Projection;

/**
 * Spring Data JPA repository for the Hall entity.
 */
@Repository
public interface HallRepository extends JpaRepository<Hall,Long>  {
	Hall findOneById(Long id);
	Hall findOneByName(String name);
	ArrayList<Hall> findAllByPlace(Place idPlace); 
	
}
