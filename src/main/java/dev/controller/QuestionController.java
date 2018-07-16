package dev.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.entites.OptionQuestion;
import dev.entites.Question;
import dev.metiers.OptionQuestionService;
import dev.metiers.QuestionService;

/**
 * QuestionController.java
 *
 * @author matth
 */
@Controller
@RequestMapping("/questions")
public class QuestionController {

	/**
	 * questionService : QuestionService
	 */
	private QuestionService questionService;
	/**
	 * optionQuestionService : OptionQuestionService
	 */
	private OptionQuestionService optionQuestionService;

	/**
	 * Constructor of QuestionController.java
	 *
	 * @param questionService
	 * @param optionQuestionService
	 */
	public QuestionController(QuestionService questionService, OptionQuestionService optionQuestionService) {
		super();
		this.questionService = questionService;
		this.optionQuestionService = optionQuestionService;
	}

	/**
	 * Fonction : Lister les questions avec une JSP
	 *
	 * @return
	 */
	@GetMapping("/lister")
	public ModelAndView lister() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeQuestions", questionService.lister());
		mv.setViewName("questions/listerQuestions");
		return mv;
	}

	/**
	 * Fonction : Affichage de la création d'une question avec une JSP
	 *
	 * @return
	 */
	@GetMapping("/creer")
	public ModelAndView creerQuestion() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeOptions", optionQuestionService.lister());
		mv.addObject("question", new Question("0", "?"));
		mv.setViewName("questions/creerQuestions");
		return mv;
	}

	/**
	 * Fonction : Créer une question avec une JSP
	 *
	 * @param question
	 * @param choixOptions
	 * @param result
	 * @return
	 */
	@PostMapping("/creer")
	public ModelAndView postCreerOption(@ModelAttribute("question") Question question,
			@RequestParam String[] choixOptions, BindingResult result) {
		questionService.getQuestionRepository().save(question);
		ArrayList<OptionQuestion> maList = new ArrayList<OptionQuestion>();
		for (String option : choixOptions) {
			maList.add(optionQuestionService.trouverAvecId(option));
		}
		question.setOptions(maList);
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeQuestions", questionService.lister());
		mv.setViewName("questions/listerQuestions");
		return mv;
	}

	/**
	 * Fonction : Affichage d'une mis-à-jour d'une question dans une JSP
	 *
	 * @return
	 */
	@GetMapping("/maj")
	public ModelAndView majOption() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeQuestions", questionService.lister());
		mv.addObject("listeOptions", optionQuestionService.lister());
		mv.addObject("question", new Question("0", "?"));
		mv.setViewName("questions/majQuestions");
		return mv;
	}

	/**
	 * Fonction : Mettre à jour une question avec une JSP
	 *
	 * @param question
	 * @param choix
	 * @param choixOptions
	 * @param result
	 * @return
	 */
	@PostMapping("/maj")
	public ModelAndView postMajOption(@ModelAttribute("question") Question question, @RequestParam String choix,
			@RequestParam String[] choixOptions, BindingResult result) {

		ArrayList<OptionQuestion> maList = new ArrayList<OptionQuestion>();
		for (String option : choixOptions) {
			maList.add(optionQuestionService.trouverAvecId(option));
		}
		question.setOptions(maList);

		question.setId(Long.parseLong(choix));
		questionService.getQuestionRepository().update(question);
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeQuestions", questionService.lister());
		mv.setViewName("questions/listerQuestions");
		return mv;
	}

	/**
	 * Fonction : Afficher la suppression d'une question avec une JSP
	 *
	 * @return
	 */
	@GetMapping("/supprimer")
	public ModelAndView supprimer() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeQuestions", questionService.lister());
		mv.addObject("listeOptions", optionQuestionService.lister());
		mv.setViewName("questions/supprimerQuestions");
		return mv;
	}

	/**
	 * Fonction : Supprimer une question avec une JSP
	 *
	 * @param choix
	 * @return
	 */
	@PostMapping("/supprimer")
	public ModelAndView postSupprimer(@RequestParam String choix) {
		questionService.getQuestionRepository().delete(questionService.trouverAvecId(choix));
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeQuestions", questionService.lister());
		mv.setViewName("questions/listerQuestions");
		return mv;
	}

}
