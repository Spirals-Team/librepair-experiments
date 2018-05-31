package dev.paie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.paie.entite.Periode;

@Repository
public interface PeriodeRepository extends JpaRepository<Periode, Integer> {

}
