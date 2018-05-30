package dev.paie.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.paie.entite.Cotisation;
import dev.paie.repository.CotisationRepository;

@RestController
@RequestMapping("/api")
public class ListerCotisationsController {

	@Autowired
	private CotisationRepository CotisationRepo;

	@RequestMapping(path = "/cotisations", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Cotisation>> listerCotisationsController() {
		List<Cotisation> listeCotisations = CotisationRepo.findAll();
		return ResponseEntity.ok(listeCotisations);
	}

	@RequestMapping(path = "/cotisations/{code}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> cotisationController(@PathVariable String code) {
		Optional<Cotisation> cotisation = CotisationRepo.findByCode(code);
		ResponseEntity<?> reponse;
		if (cotisation.isPresent()) {
			reponse = ResponseEntity.status(200).body(cotisation);
		} else {
			reponse = ResponseEntity.status(404).body("Code de cotisations non trouvé");
		}
		return reponse;
	}

	@RequestMapping(path = "/cotisations", method = RequestMethod.POST)
	@ResponseBody
	public void insererCotisationController(@RequestBody Cotisation cotisation) {
		CotisationRepo.save(cotisation);
	}

	@RequestMapping(path = "/cotisations/{code}", method = RequestMethod.PUT)
	@ResponseBody
	public void modifierCotisationController(@RequestBody Cotisation cotisation, @PathVariable String code) {
		Optional<Cotisation> cotis = CotisationRepo.findByCode(code);
		cotisation.setId(cotis.get().getId());
		CotisationRepo.save(cotisation);
	}

	@RequestMapping(path = "/cotisations/{code}", method = RequestMethod.DELETE)
	@ResponseBody
	public void supprimerCotisationController(@PathVariable String code) {
		Optional<Cotisation> cotisation = CotisationRepo.findByCode(code);
		CotisationRepo.delete(cotisation.get());
	}

}
