package poe.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poe.spring.domain.Trajet;
import poe.spring.domain.User;
import poe.spring.repository.TrajetRepository;
import poe.spring.repository.UserRepository;
import java.util.Date;

@Service
public class TrajetManagerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrajetRepository trajetRepository;
    public Trajet proposerUnTrajet(Long userId, String villeDepart,
                                   String villeArrivee, Date dateDepart,
                                   Integer prix, Integer nombreDePlace) throws Exception {
        Trajet trajet;
        User conducteur = userRepository.findOne(userId);
        if (conducteur != null) {
            trajet = new Trajet();
            trajet.setVilleDepart(villeDepart);
            trajet.setVilleArrivee(villeArrivee);
            trajet.setDateDepart(dateDepart);
            trajet.setPrix(prix);
            trajet.setNbPlaces(nombreDePlace);
            trajet.setConducteur(conducteur);
            conducteur.getTrajets().add(trajet);
            trajet = trajetRepository.save(trajet);
            userRepository.save(conducteur);
        } else {
            throw new Exception("conducteur introuvable");
        }
        return trajet;
    }
}