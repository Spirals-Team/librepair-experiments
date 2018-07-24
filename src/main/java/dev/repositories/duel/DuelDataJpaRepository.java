package dev.repositories.duel;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.Duel;

public interface DuelDataJpaRepository extends JpaRepository<Duel, Long> {

}
