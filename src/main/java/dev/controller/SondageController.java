package dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.entites.Classe;
import dev.entites.OptionSondage;
import dev.entites.Sondage;
import dev.metiers.ClasseService;
import dev.metiers.OptionSondageService;
import dev.metiers.SondageService;

@Controller
@RequestMapping("/sondages")
public class SondageController {

	private SondageService sondageService;
	private ClasseService classeService;
	private OptionSondageService optionSondageService;

	public SondageController(SondageService sondageService, ClasseService classeService,
			OptionSondageService optionSondageService) {
		super();
		this.sondageService = sondageService;
		this.classeService = classeService;
		this.optionSondageService = optionSondageService;
	}

	@GetMapping("/lister")
	public ModelAndView lister() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeSondages", sondageService.lister());
		mv.setViewName("sondages/listerSondages");
		return mv;
	}

	@GetMapping("/ajouter")
	public ModelAndView save() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeClasse", classeService.lister());
		mv.addObject("listeOption", optionSondageService.lister());
		mv.addObject("sondage", new Sondage());
		mv.setViewName("sondages/ajouterSondage");
		return mv;
	}

	@PostMapping("/ajouter")
	public ModelAndView ajouter(@ModelAttribute("sondage") @Valid Sondage sondage, BindingResult result,
			@RequestParam("opt") List<Long> opt) {

		ModelAndView mv = new ModelAndView();

		if (result.hasErrors()) {

			mv.addObject("listeClasse", classeService.lister());
			mv.addObject("listeOption", optionSondageService.lister());
			mv.addObject("sondage", sondage);
			mv.setViewName("sondages/ajouterSondage");

		} else {
			for (Classe c : classeService.lister()) {
				if (c.getId() == sondage.getClasse().getId()) {
					sondage.setClasse(c);
				}
			}

			for (OptionSondage os : optionSondageService.lister()) {
				for (Long indice : opt) {
					if (os.getId().equals(indice)) {
						sondage.getOptions().add(os);
					}
				}
			}
			Long id = 0L;
			for (Sondage s : sondageService.lister()) {
				if (id >= s.getId()) {
					id = s.getId();
					id++;
				}
			}
			sondage.setId(id);
			sondageService.save(sondage);
			mv.setViewName("redirect:/sondages/lister");
		}
		return mv;
	}
}
