package dev.repositories.concours;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.entites.Concours;

public interface ConcoursDataJpaRepo extends JpaRepository<Concours, Long> {
	
	@Query("select c from Concours c JOIN c.participants p where p.id = :idStagiaire")
    List<Concours> findConcoursByStagiaire(@Param("idStagiaire") Long idStagiaire);

}
