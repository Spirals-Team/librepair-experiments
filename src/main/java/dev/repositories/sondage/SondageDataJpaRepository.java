package dev.repositories.sondage;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.Sondage;

public interface SondageDataJpaRepository extends JpaRepository<Sondage, Long> {

}
