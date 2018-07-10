package dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.metiers.ExamenService;

@Controller
@RequestMapping("/examens")
public class ExamenController {

	private ExamenService examenService;

	public ExamenController(ExamenService examenService) {
		super();
		this.examenService = examenService;
	}

	@GetMapping("/lister")
	public ModelAndView lister() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("examList", examenService.lister());
		mv.setViewName("examens/listerExamens");
		return mv;
	}
}
