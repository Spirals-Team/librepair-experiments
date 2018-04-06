package com.app.repositories;

import com.app.entities.Incident;
import com.app.entities.Agent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


@Repository
public interface IncidentRepository extends CrudRepository<Incident,Long>{
    @Query("SELECT i FROM Incident i WHERE i.agent = ?1")
    List<Incident> findByAgent(Agent agent);
}
