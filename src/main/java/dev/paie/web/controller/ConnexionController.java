package dev.paie.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/connexion")
public class ConnexionController {

	// @GetMapping_ est un raccourci pour @RequestMapping(method =
	// RequestMethod.GET)
	@GetMapping
	public String afficherPageCreer() {
		return "connexion";
	}

}