package dev.paie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.paie.entite.Avantage;

//                                               //type de l'objet / type de la clé primaire
public interface AvantageRepository extends JpaRepository<Avantage, Integer>{
	
}
