package dev.paie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.paie.entite.BulletinSalaire;

@Repository
public interface BulletinSalaireRepository extends JpaRepository<BulletinSalaire, Integer> {

}
