package dev.repositories.duel;

import java.util.Optional;

import dev.entites.Duel;
import dev.repositories.CrudRepository;

public interface DuelRepository extends CrudRepository<Duel> {

	Optional<Duel> findById(Long id);

}
