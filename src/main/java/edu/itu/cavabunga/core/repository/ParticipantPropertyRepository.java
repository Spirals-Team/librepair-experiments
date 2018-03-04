package edu.itu.cavabunga.core.repository;

import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.ParticipantProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantPropertyRepository extends JpaRepository<ParticipantProperty, Long>{
    ParticipantProperty findByParticipant(Participant participant);
}
