package dev.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.entites.Classe;
import dev.entites.Stagiaire;
import dev.metiers.ClasseService;
import dev.metiers.StagiaireService;

@Controller
@RequestMapping("/classes")
public class ClasseController {
	private ClasseService classeService;
	private StagiaireService stagiaireService;

	public ClasseController(ClasseService classeService, StagiaireService stagiaireService) {
		super();
		this.classeService = classeService;
		this.stagiaireService = stagiaireService;
	}

	@GetMapping("/lister")
	public ModelAndView lister() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeClasses", classeService.lister());
		mv.setViewName("classes/listerClasses");
		return mv;
	}

	@GetMapping("/ajouter")
	public ModelAndView getSave() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("classe", new Classe());
		mv.addObject("listeStagiaires", stagiaireService.lister());
		mv.setViewName("classes/ajouterClasses");
		return mv;
	}

	@PostMapping("/ajouter")
	public ModelAndView postSave(@ModelAttribute("classe") @Valid Classe c, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		c.setId(classeService.lister().size() + 1L);
		List<Stagiaire> listeStagiaireClasse = c.getStagiaires();
		List<Stagiaire> listeStagiaireTotal = stagiaireService.lister();
		List<Stagiaire> listeStagiaireModfie = new ArrayList();

		for (Stagiaire st : listeStagiaireClasse) {
			if (st.getId() != null) {
				for (Stagiaire st2 : listeStagiaireTotal) {
					if (st.getId() == st2.getId()) {
						st.setNom(st2.getNom());
						st.setPrenom(st2.getPrenom());
						st.setEmail(st2.getEmail());
						st.setPhotoUrl(st2.getPhotoUrl());
						listeStagiaireModfie.add(st);
						st.setClasse(c);
					}
				}
			}
		}

		c.setStagiaires(listeStagiaireModfie);

		if (result.hasErrors()) {
			mv.addObject("classe", c);
			mv.addObject("listeStagiaires", stagiaireService.lister());
			mv.setViewName("classes/ajouterClasses");
		} else {
			classeService.ajouter(c);
			mv.setViewName("redirect:/classes/lister");
		}

		return mv;
	}

	@GetMapping("/maj")
	public ModelAndView getMajFromId(@RequestParam("id") Long id) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("classe", classeService.trouverClasseParId(id));
		mv.addObject("listeStagiairesClasse", classeService.trouverClasseParId(id).getStagiaires());
		mv.addObject("listeStagiaires", stagiaireService.lister());
		mv.setViewName("classes/majClasse");
		return mv;
	}

	@PutMapping("/maj")
	public ModelAndView putMaj(@ModelAttribute("classe") @Valid Classe c, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		List<Stagiaire> listeStagiaireClasse = c.getStagiaires();
		List<Stagiaire> listeStagiaireTotal = stagiaireService.lister();
		List<Stagiaire> listeStagiaireModfie = new ArrayList();

		for (Stagiaire st : listeStagiaireClasse) {
			if (st.getId() != null) {
				for (Stagiaire st2 : listeStagiaireTotal) {
					if (st.getId() == st2.getId()) {
						st.setNom(st2.getNom());
						st.setPrenom(st2.getPrenom());
						st.setEmail(st2.getEmail());
						st.setPhotoUrl(st2.getPhotoUrl());
						listeStagiaireModfie.add(st);
						st.setClasse(c);
					}
				}
			}
		}

		c.setStagiaires(listeStagiaireModfie);

		if (result.hasErrors()) {
			mv.addObject("classe", c);
			mv.addObject("listeStagiaires", stagiaireService.lister());
			mv.setViewName("classes/majClasse");
		} else {
			classeService.maj(c);
			mv.setViewName("redirect:/classes/lister");
		}

		return mv;
	}

	@GetMapping("/supprimer")
	public ModelAndView getSupprimerFromId(@RequestParam("id") Long id) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("classe", classeService.trouverClasseParId(id));
		mv.addObject("listeStagiairesClasse", classeService.trouverClasseParId(id).getStagiaires());
		mv.setViewName("classes/supprimerClasses");
		return mv;
	}

	@PostMapping("/supprimer")
	public ModelAndView postSupprimer(@RequestParam("id") Long id) {
		ModelAndView mv = new ModelAndView();
		classeService.supprimer(classeService.trouverClasseParId(id));
		mv.setViewName("redirect:/classes/lister");
		return mv;
	}

}
