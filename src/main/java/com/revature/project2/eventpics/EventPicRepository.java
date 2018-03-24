package com.revature.project2.eventpics;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventPicRepository extends CrudRepository<EventPic, Integer>{
}
