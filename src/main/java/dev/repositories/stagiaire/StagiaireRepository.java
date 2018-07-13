package dev.repositories.stagiaire;

import java.util.Optional;

import dev.entites.Stagiaire;
import dev.repositories.CrudRepository;

public interface StagiaireRepository extends CrudRepository<Stagiaire> {

	public Optional<Stagiaire> findById(Long id);

}
