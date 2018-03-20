package com.revature.project2.eventratings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventRatingService {
    private final EventRatingRepository eventRatingRepository;

    @Autowired
    public EventRatingService(EventRatingRepository eventRatingRepository){
        this.eventRatingRepository = eventRatingRepository;
    }
    public Optional<EventRating> findByEventRatingId(int id){
        return eventRatingRepository.findById(id);
    }
    public Iterable<EventRating> findAllEventRatings(){
        return eventRatingRepository.findAll();
    }
}
