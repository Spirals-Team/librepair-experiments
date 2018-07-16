package dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.entites.OptionQuestion;
import dev.metiers.OptionQuestionService;

/**
 * OptionQuestionController.java
 *
 * @author matth
 */
@Controller
@RequestMapping("/questions")
public class OptionQuestionController {

	/**
	 * optionQuestionService : OptionQuestionService
	 */
	private OptionQuestionService optionQuestionService;

	/**
	 * Constructor of OptionQuestionController.java
	 *
	 * @param optionQuestionService
	 */
	public OptionQuestionController(OptionQuestionService optionQuestionService) {
		super();
		this.optionQuestionService = optionQuestionService;
	}

	/**
	 * Fonction : Lister les options avec une JSP
	 *
	 * @return
	 */
	@GetMapping("/listerOption")
	public ModelAndView lister() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeOptionQuestions", optionQuestionService.lister());
		mv.setViewName("questions/listerOptionQuestions");
		return mv;
	}

	/**
	 * Fonction : Afficher la création d'une option avec une JSP
	 *
	 * @return
	 */
	@GetMapping("/creerOption")
	public ModelAndView creerOption() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("option", new OptionQuestion("0", "?", "false"));
		mv.setViewName("questions/creerOptionQuestions");
		return mv;
	}

	/**
	 * Fonction : Créer une option avec une JSP
	 *
	 * @param option
	 * @param result
	 * @return
	 */
	@PostMapping("/creerOption")
	public ModelAndView postCreerOption(@ModelAttribute("option") OptionQuestion option, BindingResult result) {
		optionQuestionService.getOptionQuestionRepository().save(option);
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeOptionQuestions", optionQuestionService.lister());
		mv.setViewName("questions/listerOptionQuestions");
		return mv;
	}

	/**
	 * Fonction : Afficher la mise à jour d'une option avec une JSP
	 *
	 * @return
	 */
	@GetMapping("/majOption")
	public ModelAndView majOption() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeOptionQuestions", optionQuestionService.lister());
		mv.addObject("option", new OptionQuestion("0", "?", "false"));
		mv.setViewName("questions/majOptionQuestions");
		return mv;
	}

	/**
	 * Fonction : Mettre à jour une option avec une JSP
	 *
	 * @param option
	 * @param choix
	 * @param result
	 * @return
	 */
	@PostMapping("/majOption")
	public ModelAndView postMajOption(@ModelAttribute("option") OptionQuestion option, @RequestParam String choix,
			BindingResult result) {
		option.setId(Long.parseLong(choix));
		optionQuestionService.getOptionQuestionRepository().update(option);
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeOptionQuestions", optionQuestionService.lister());
		mv.setViewName("questions/listerOptionQuestions");
		return mv;
	}

	/**
	 * Fonction : Afficher la suppression d'une question avec une JSP
	 *
	 * @return
	 */
	@GetMapping("/supprimerOption")
	public ModelAndView supprimerOption() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeOptionQuestions", optionQuestionService.lister());
		mv.setViewName("questions/supprimerOptionQuestions");
		return mv;
	}

	/**
	 * Fonction : Supprimer une question avec une JSP
	 *
	 * @param choix
	 * @return
	 */
	@PostMapping("/supprimerOption")
	public ModelAndView postSupprimerOption(@RequestParam String choix) {
		optionQuestionService.getOptionQuestionRepository().delete(optionQuestionService.trouverAvecId(choix));
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeOptionQuestions", optionQuestionService.lister());
		mv.setViewName("questions/listerOptionQuestions");
		return mv;
	}

}
