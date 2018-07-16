package dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.metiers.ConcoursService;

@Controller
@RequestMapping("/concours")
public class ConcoursController {

	private ConcoursService concoursService;

	public ConcoursController(ConcoursService concoursService) {
		super();
		this.concoursService = concoursService;
	}

	@GetMapping("/lister")
	public ModelAndView lister() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeConcours", concoursService.lister());
		mv.setViewName("concours/listerConcours");
		return mv;
	}

}
