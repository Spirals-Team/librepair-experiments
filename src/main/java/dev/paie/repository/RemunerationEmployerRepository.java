package dev.paie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.paie.entite.RemunerationEmploye;

public interface RemunerationEmployerRepository extends JpaRepository<RemunerationEmploye, Integer> {

}
