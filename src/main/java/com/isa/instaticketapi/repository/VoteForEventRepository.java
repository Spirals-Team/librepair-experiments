package com.isa.instaticketapi.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.Event;
import com.isa.instaticketapi.domain.VoteForEvent;

/**
 * Spring Data JPA repository for the vote for projection entity.
 */
@Repository
public interface VoteForEventRepository extends JpaRepository<VoteForEvent, Long> {
	ArrayList<VoteForEvent> findAllByEvent(Event event);
}
