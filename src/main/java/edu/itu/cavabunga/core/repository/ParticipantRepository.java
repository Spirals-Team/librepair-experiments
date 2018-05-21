package edu.itu.cavabunga.core.repository;

import edu.itu.cavabunga.core.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByUserName(String name);

    Optional<Participant> findById(Long Id);

}
