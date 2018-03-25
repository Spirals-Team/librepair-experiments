package com.revature.project2.events;

import com.revature.project2.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
  Event findByStartDateTime(LocalDateTime time);
  @Query("SELECT event.attendees from Event event where event.id = :id")
  Set<User> getAttendeesById(@Param("id") int id);
}
