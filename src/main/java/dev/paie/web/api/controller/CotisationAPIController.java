package dev.paie.web.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.paie.entite.Cotisation;
import dev.paie.repository.CotisationRepository;

@RestController

public class CotisationAPIController {

	private CotisationRepository repoCotisation;

	@Autowired
	public CotisationAPIController(CotisationRepository repoCotisation) {
		super();
		this.repoCotisation = repoCotisation;
	}

	@RequestMapping(value = "/cotisations", method = RequestMethod.GET)
	public List<Cotisation> listeCotisation() {
		return repoCotisation.findAll();
	}

	@RequestMapping(value = "/cotisations/{cotisationCode}", method = RequestMethod.GET)
	public ResponseEntity uneCotisation(@PathVariable String cotisationCode) {
		ResponseEntity responseEntity;
		Optional<Cotisation> opt = repoCotisation.findByCode(cotisationCode);
		if (opt.isPresent()) {
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(opt.get());
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Votre cotisation n'existe pas");
		}
		return responseEntity;
	}

	@RequestMapping(value = "/cotisations", method = RequestMethod.POST)
	public ResponseEntity<String> nouvelleCotisation(@RequestBody Cotisation cotisation) {
		Optional<Cotisation> opt = repoCotisation.findByCode(cotisation.getCode());
		// ModelAndView mv = new ModelAndView()
		ResponseEntity<String> responseEntity;
		if (opt.isPresent()) {
			// mv.
			responseEntity = ResponseEntity.status(HttpStatus.CONFLICT)
					.body("votre Cotisation à déjà son code en base");
		} else {
			repoCotisation.save(cotisation);
			responseEntity = ResponseEntity.status(HttpStatus.OK).body("Votre Cotisation est accepté");
		}

		return responseEntity;
	}

	@RequestMapping(value = "/cotisations", method = RequestMethod.PUT)

	public ResponseEntity<String> majCotisation(@RequestBody Cotisation cotisation) {
		Optional<Cotisation> opt = repoCotisation.findByCode(cotisation.getCode());

		ResponseEntity<String> responseEntity;
		if (opt.isPresent()) {
			cotisation.setId(opt.get().getId());
			repoCotisation.save(cotisation);
			responseEntity = ResponseEntity.status(HttpStatus.OK).body("Votre Cotisation est modifier");

		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Votre cotisation n'existe pas");

		}

		return responseEntity;
	}

	@RequestMapping(value = "/cotisations/{code}", method = RequestMethod.DELETE)
	public ResponseEntity<String> suppCotisation(@PathVariable String code) {

		ResponseEntity<String> responseEntity;
		Optional<Cotisation> opt = repoCotisation.findByCode(code);
		if (opt.isPresent()) {
			repoCotisation.delete(opt.get());
			responseEntity = ResponseEntity.status(HttpStatus.OK).body("Votre Cotisation est supprimer");

		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Votre cotisation n'existe pas");

		}

		return responseEntity;
	}
}
