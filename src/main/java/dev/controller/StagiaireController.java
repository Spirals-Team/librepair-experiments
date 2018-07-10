package dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.metiers.StagiaireService;

@Controller
@RequestMapping("/stagiaires")
public class StagiaireController {

	private StagiaireService stagiaireService;

	public StagiaireController(StagiaireService stagiaireService) {
		super();
		this.stagiaireService = stagiaireService;
	}

	@GetMapping("/lister")
	public ModelAndView lister() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeStagiaires", stagiaireService.lister());
		mv.setViewName("stagiaires/listerStagiaires");
		return mv;
	}
}
