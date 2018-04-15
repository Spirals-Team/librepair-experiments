package com.isa.instaticketapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.Bid;
import com.isa.instaticketapi.domain.Event;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

	Bid findOneById(Long id);

}
