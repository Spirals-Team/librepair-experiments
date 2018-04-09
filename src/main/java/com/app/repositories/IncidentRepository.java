package com.app.repositories;

import com.app.entities.Incident;
import com.app.entities.Agent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface IncidentRepository extends MongoRepository<Incident,Long>{
    @Query("SELECT i FROM Incident i WHERE i.agent = ?1")
    List<Incident> findByAgentID(String agent);
}
