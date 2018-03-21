package com.revature.project2.comments;

import com.revature.project2.events.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
  Iterable<Comment> findByEvent(Event event);
}
