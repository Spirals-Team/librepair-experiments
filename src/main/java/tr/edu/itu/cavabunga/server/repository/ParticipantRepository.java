package tr.edu.itu.cavabunga.server.repository;

import tr.edu.itu.cavabunga.lib.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByUserName(String name);

    Optional<Participant> findById(Long Id);

}
