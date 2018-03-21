package com.revature.project2.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
  Event findByStartDateTime(LocalDateTime time);
}
