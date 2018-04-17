package com.isa.instaticketapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.OfferRequest;
import com.isa.instaticketapi.domain.Place;

@Repository
public interface OfferRequestRepository extends JpaRepository<OfferRequest, Long> {
	
	
	
	OfferRequest findOneById(Long id);
	
	


}
