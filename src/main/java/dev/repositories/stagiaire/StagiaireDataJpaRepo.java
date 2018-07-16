package dev.repositories.stagiaire;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.Stagiaire;

public interface StagiaireDataJpaRepo extends JpaRepository<Stagiaire, Long> {

}
