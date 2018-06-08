package de.dhbw.softwareengineering.digitaljournal.persistence;

import de.dhbw.softwareengineering.digitaljournal.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, String>{

    List<Goal> findAllByUsernameOrderByDateDesc(String name);

    @Transactional
    void deleteAllByUsername(String username);
}
