package edu.itu.cavabunga.core.repository;

import edu.itu.cavabunga.core.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Participant findByUserName(String name);

    @Query("select a from Participant a where type=?1")
    List<Participant> findByType(String type);

    void deleteByUserName(String userName);

    Long countParticipantById(Long participantId);

    Long countParticipantByUserName(String userName);
}
