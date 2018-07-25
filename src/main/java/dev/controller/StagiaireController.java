package dev.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.entites.Stagiaire;
import dev.metiers.StagiaireService;

@Controller
@RequestMapping("/stagiaires")
public class StagiaireController {

	private StagiaireService stagiaireService;

	public StagiaireController(StagiaireService stagiaireService) {
		super();
		this.stagiaireService = stagiaireService;
	}

	// lister
	@GetMapping("/lister")
	public ModelAndView lister() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeStagiaires", stagiaireService.lister());
		mv.setViewName("stagiaires/listerStagiaires");
		return mv;
	}

	// creer
	@GetMapping("/creer")
	public ModelAndView save() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("stagiaire", new Stagiaire());
		mv.setViewName("stagiaires/creerStagiaires");
		return mv;
	}

	@PostMapping("/creer")
	public ModelAndView creerForm(@ModelAttribute("stagiaire") @Valid Stagiaire s, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		if (result.hasErrors()) {
			// si erreur renvoyer le formulaire avec les erreurs de form error
			mv.addObject("stagiaire", s);
			mv.setViewName("stagiaires/creerStagiaires");
		} else {
			// si tout est ok on sauvegarde le nouveau stagiaire et on redirige
			// vers lister;
			stagiaireService.save(s);
			mv.setViewName("redirect:/stagiaires/lister");
		}
		return mv;
	}

	// editer
	@GetMapping("/editer")
	public ModelAndView FormFromId(@RequestParam("id") Long id) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("stagiaire", stagiaireService.findStagiaireById(id));
		mv.setViewName("stagiaires/editerStagiaires");
		return mv;
	}

	@PostMapping("/editer")
	public ModelAndView editerForm(@ModelAttribute("stagiaire") @Valid Stagiaire s, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		if (result.hasErrors()) {
			// si erreur renvoyer le formulaire avec les erreurs de form error
			mv.addObject("stagiaire", s);
			mv.setViewName("stagiaires/editerStagiaires");
		} else {
			// si tout est ok on sauvegarde les modifications du stagiaire et on
			// redirige
			// vers lister;
			stagiaireService.update(s);
			mv.setViewName("redirect:/stagiaires/lister");
		}
		return mv;
	}

	// supprimer
	@GetMapping("/supprimer")
	public ModelAndView FormFromIdDelete(@RequestParam("id") Long id) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("stagiaire", stagiaireService.findStagiaireById(id));
		mv.setViewName("stagiaires/supprimerStagiaires");
		return mv;
	}

	@PostMapping("/supprimer")
	public ModelAndView supprimerForm(@RequestParam("id") Long id) {
		ModelAndView mv = new ModelAndView();
		stagiaireService.delete(stagiaireService.findStagiaireById(id));
		mv.setViewName("redirect:/stagiaires/lister");
		return mv;
	}
}
