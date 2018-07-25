package dev.repositories.classe;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.Classe;

public interface ClasseDataJpaRepo extends JpaRepository<Classe, Long> {

	@EntityGraph(attributePaths = "stagiaires", type = EntityGraphType.LOAD)
	@Override
	List<Classe> findAll();
}
