package poe.spring.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poe.spring.api.dto.ProposerUnTrajetDto;
import poe.spring.domain.Trajet;
import poe.spring.repository.TrajetRepository;
import poe.spring.repository.UserRepository;
import poe.spring.service.TrajetManagerService;

@RestController
@RequestMapping("/api/trajet")
public class TrajetRestController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TrajetRepository trajetRepository;
	@Autowired
	private TrajetManagerService trajetManagerService;

	@PostMapping
	public ProposerUnTrajetDto save(@RequestBody ProposerUnTrajetDto trajetDto) throws Exception {
		Trajet trajet = trajetManagerService.proposerUnTrajet(trajetDto.getConducteurId(), trajetDto.getVilleDepart(),
				trajetDto.getVilleArrivee(), trajetDto.getDateDepart(), trajetDto.getPrix(), trajetDto.getNbPlaces());
		return trajetDto;
	}
}