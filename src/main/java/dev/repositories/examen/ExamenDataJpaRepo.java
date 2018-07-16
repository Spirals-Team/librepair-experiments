package dev.repositories.examen;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.Examen;

public interface ExamenDataJpaRepo extends JpaRepository<Examen, Long> {

}
