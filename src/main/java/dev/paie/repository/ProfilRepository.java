package dev.paie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.paie.entite.ProfilRemuneration;

@Repository
public interface ProfilRepository extends JpaRepository<ProfilRemuneration, Integer> {

}
