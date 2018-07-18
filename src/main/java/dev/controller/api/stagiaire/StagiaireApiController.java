package dev.controller.api.stagiaire;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Stagiaire;
import dev.metiers.StagiaireService;

@RestController
@RequestMapping(value = "/api/stagiaire")
public class StagiaireApiController {

	private StagiaireService stagiaireService;

	public StagiaireApiController(StagiaireService stagiaireService) {
		super();
		this.stagiaireService = stagiaireService;
	}

	// lister
	@GetMapping
	public ResponseEntity<List<StagiaireVm>> findAll() {
		List<Stagiaire> listeStagiaire = this.stagiaireService.lister();

		// transforme une liste de stagiaire en une autre liste de stagiaire qui
		// contient les attributs souhaités
		List<StagiaireVm> listeStagiaireVm = listeStagiaire.stream().map(unStagiaire -> new StagiaireVm(unStagiaire))
				.collect(Collectors.toList());

		// En précisant en "dur" le code de la réponse via la méthode statique
		// status
		// return ResponseEntity.status(200).body(allGrades);

		// HttpStatus est une énumération regroupant les codes HTTP usuels
		return ResponseEntity.status(HttpStatus.OK).body(listeStagiaireVm);
	}
}
