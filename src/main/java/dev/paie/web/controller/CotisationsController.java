package dev.paie.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.paie.entite.Cotisation;
import dev.paie.repository.CotisationRepository;

@Controller
@RequestMapping("/api")
@RestController
public class CotisationsController {

	@Autowired
	CotisationRepository cotisation;

	@RequestMapping(value = "/cotisations", method = RequestMethod.GET)
	public List<Cotisation> findAllCotisations() {
		return cotisation.findAll();
	}

	@RequestMapping(value = "/cotisations/{code}", method = RequestMethod.GET)
	public ResponseEntity findCotisationByCode(@PathVariable String code) {
		Cotisation res = cotisation.findByCode(code);
		if (res == null) {
			Map<String, String> reponse = new HashMap<>();
			reponse.put("message", "Code de cotisation non trouvé");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reponse);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(cotisation.findByCode(code));
		}
	}

	@RequestMapping(value = "/cotisations", method = RequestMethod.POST)
	public void addCotisation(@RequestBody Cotisation toAdd) {
		cotisation.save(toAdd);
	}

	@RequestMapping(value = "/cotisations/{code}", method = RequestMethod.PUT)
	public void addCotisationViaCode(@RequestBody Cotisation toUpdate, @PathVariable String code) {
		Integer id = cotisation.findByCode(code).getId();
		toUpdate.setCode(code);
		toUpdate.setId(id);
		cotisation.save(toUpdate);
	}

	@RequestMapping(value = "/cotisations/{code}", method = RequestMethod.DELETE)
	public void deleteCotisationViaCode(@PathVariable String code) {
		cotisation.delete(cotisation.findByCode(code));
	}

}
