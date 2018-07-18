package dev.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.entites.OptionSondage;
import dev.metiers.OptionSondageService;

@Controller
@RequestMapping("/sondages/options")
public class OptionSondageController {

	private OptionSondageService optionSondageService;

	public OptionSondageController(OptionSondageService optionSondageService) {
		super();
		this.optionSondageService = optionSondageService;
	}

	@GetMapping("/lister")
	public ModelAndView lister() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeOptionSondage", optionSondageService.lister());
		mv.setViewName("optionSondages/listerOptionSondages");
		return mv;
	}

	@GetMapping("/ajouter")
	public ModelAndView ajouter() {
		return new ModelAndView("optionSondages/ajouterOptionSondage", "optionsondage", new OptionSondage());
	}

	@PostMapping("/ajouter")
	public ModelAndView save(@ModelAttribute("optionsondage") @Valid OptionSondage optionSondage, BindingResult result,
			ModelMap model) {

		ModelAndView mv = new ModelAndView();

		if (result.hasErrors()) {
			mv.addObject("optionsondage", optionSondage);
			mv.setViewName("optionSondages/ajouterOptionSondage");
		} else {
			Long id = 0L;
			for (OptionSondage os : optionSondageService.lister()) {
				if (id >= os.getId()) {
					id = os.getId();
					id++;
				}
			}
			optionSondage.setId(id);
			optionSondageService.save(optionSondage);
			mv.setViewName("redirect:/sondages/options/lister");
		}
		return mv;
	}
}
