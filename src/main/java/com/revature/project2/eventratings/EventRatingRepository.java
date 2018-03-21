package com.revature.project2.eventratings;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRatingRepository extends CrudRepository<EventRating,Integer> {

}
