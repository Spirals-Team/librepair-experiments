package dev.paie.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.paie.entite.Cotisation;
import dev.paie.repository.CotisationRepository;
import dev.paie.web.controller.exception.CodeNotFoundException;

@RestController
public class CotisationController {

	@Autowired
	CotisationRepository cor;

	@RequestMapping(value = "/api/cotisations", method = RequestMethod.GET)
	@Secured({ "ROLE_UTILISATEUR", "ROLE_ADMINISTRATEUR" })
	public List<Cotisation> getAllCotisation() {
		return cor.findAll();
	}

	@RequestMapping(value = "/api/cotisations/{code}", method = RequestMethod.GET)
	@Secured({ "ROLE_UTILISATEUR", "ROLE_ADMINISTRATEUR" })
	public ResponseEntity<Cotisation> findClient(@PathVariable String code) {
		// return cor.findByCode(code).map(cot -> ResponseEntity.ok(cot))
		// .orElseThrow(() -> new CodeNotFoundException(code));
		Cotisation cot = cor.findByCode(code);
		if (cot != null) {
			return ResponseEntity.ok(cot);
		} else {
			throw new CodeNotFoundException(code);
		}

	}

	@RequestMapping(value = "/api/cotisations", method = RequestMethod.POST)
	@Secured("ROLE_ADMINISTRATEUR")
	public void insertCotisation(@RequestBody Cotisation cotisation) {
		cor.save(cotisation);
	}

	@RequestMapping(value = "/api/cotisations/{code}", method = RequestMethod.PUT)
	@Secured("ROLE_ADMINISTRATEUR")
	public void updateCotisation(@PathVariable String code, @RequestBody Cotisation cotisation) {
		cotisation.setId(cor.findByCode(code).getId());
		cor.save(cotisation);
	}

	@RequestMapping(value = "/api/cotisations/{code}", method = RequestMethod.DELETE)
	@Secured("ROLE_ADMINISTRATEUR")
	public void deleteCotisation(@PathVariable String code) {
		cor.delete(cor.findByCode(code));
	}

}
