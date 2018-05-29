package dev.paie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.paie.entite.RemunerationEmploye;

public interface RemunerationEmployeRepository extends JpaRepository<RemunerationEmploye, Integer> {

	List<String> findDistinctMatriculeBy();

}