package dev.paie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.paie.entite.Cotisation;

public interface CotisationRepository extends JpaRepository<Cotisation, Integer> {
	Optional<Cotisation> findByCode(String code);

}
