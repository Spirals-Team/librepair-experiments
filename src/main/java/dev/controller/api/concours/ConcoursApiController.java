package dev.controller.api.concours;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.api.viewModels.concours.ConcoursVm;
import dev.entites.Concours;
import dev.metiers.ConcoursService;

@RestController
@RequestMapping(value = "/api/concours")
@CrossOrigin
public class ConcoursApiController {

	private ConcoursService concoursService;

	public ConcoursApiController(ConcoursService concoursService) {
		super();
		this.concoursService = concoursService;
	}

	// lister
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ConcoursVm>> findAll() {
		List<Concours> listeConcours = this.concoursService.list();

		List<ConcoursVm> listeConcoursVm = listeConcours.stream().map(ConcoursVm::new)
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(listeConcoursVm);
	}
	
	@RequestMapping(method = RequestMethod.GET, params = {"idStagiaire"})
	public ResponseEntity<List<ConcoursVm>> findAll2(@RequestParam(value="idStagiaire")Long idStagiaire) {
		
		List<Concours> listeConcours = this.concoursService.getConcoursOfStagiaire(idStagiaire);

		List<ConcoursVm> listeConcoursVm = listeConcours.stream().map(ConcoursVm::new)
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(listeConcoursVm);
	}

}
