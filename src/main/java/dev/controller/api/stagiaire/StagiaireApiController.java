package dev.controller.api.stagiaire;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Stagiaire;
import dev.metiers.StagiaireService;

@RestController
@RequestMapping(value = "/api/stagiaires")
@CrossOrigin 	
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

		// HttpStatus est une énumération regroupant les codes HTTP usuels
		return ResponseEntity.status(HttpStatus.OK).body(listeStagiaireVm);
	}

	// créer
	@PostMapping
	public ResponseEntity<StagiaireCreeVm> creer(@RequestBody StagiaireVm stagiaireVm) {
		Stagiaire stagiaire = new Stagiaire();
		stagiaire.setNom(stagiaireVm.getNom());
		stagiaire.setPrenom(stagiaireVm.getPrenom());
		stagiaire.setEmail(stagiaireVm.getEmail());
		stagiaire.setPhotoUrl(stagiaireVm.getPhotoUrl());
		stagiaireService.save(stagiaire);

		return ResponseEntity.status(HttpStatus.OK).body(new StagiaireCreeVm(stagiaire.getId()));

	}

}
