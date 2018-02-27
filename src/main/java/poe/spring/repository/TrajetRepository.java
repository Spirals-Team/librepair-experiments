package poe.spring.repository;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import poe.spring.domain.Trajet;

@Repository
public interface TrajetRepository extends CrudRepository<Trajet, Long> {
	Trajet findById(Long id);
	Trajet findByVilleDepart(String villeDepart);
	Trajet findByVilleArrivee(String villeArrivee);
	Trajet findByDateDepart(Date dateDepart);
	Trajet findByPrix(Integer prix);
	Trajet findByNbPlaces(Integer nbPlaces);
	
}