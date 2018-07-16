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

import dev.entites.Quizz;
import dev.metiers.QuestionService;
import dev.metiers.QuizzService;

@Controller
@RequestMapping("/quizzes")
public class QuizzController {

	private QuizzService quizzService;
	private QuestionService questionService;

	public QuizzController(QuizzService quizzService, QuestionService questionService) {
		super();
		this.quizzService = quizzService;
		this.questionService = questionService;

	}

	@GetMapping("/lister")
	public ModelAndView lister() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("listeQuizz", quizzService.lister());
		mv.setViewName("quizzes/listerQuizz");
		return mv;
	}

	@GetMapping("/creer")
	public ModelAndView save() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("quizz", new Quizz());
		mv.addObject("questions", questionService.lister());
		mv.setViewName("quizzes/creerQuizz");
		return mv;
	}

	@PostMapping("/creer")
	public ModelAndView postForm(@ModelAttribute("quizz") @Valid Quizz quizz, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		if (result.hasErrors()) {
			// si erreur renvoyer le formulaire avec les erreurs de form error
			mv.addObject("questions", questionService.lister());
			mv.addObject("quizz", quizz);
			mv.setViewName("quizzes/creerQuizz");
		} else {
			// si tout est ok on sauvegarde le nouveau stagiaire et on redirige
			// vers lister;
			quizzService.save(quizz);
			mv.setViewName("redirect:/quizzes/lister");
		}
		return mv;
	}

	@GetMapping("/editer")
	public ModelAndView FormFromId(@RequestParam("id") Long id) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("questions", questionService.lister());
		mv.addObject("quizz", quizzService.findQuizzById(id));
		mv.setViewName("quizzes/creerQuizz");
		return mv;
	}

	@PostMapping("/editer")
	public ModelAndView editerForm(@ModelAttribute("quizz") @Valid Quizz quizz, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		if (result.hasErrors()) {
			// si erreur renvoyer le formulaire avec les erreurs de form error
			mv.addObject("questions", questionService.lister());
			mv.addObject("quizz", quizz);
			mv.setViewName("quizzes/creerQuizz");
		} else {
			// si tout est ok on sauvegarde les modifications du stagiaire et on
			// redirige
			// vers lister;
			quizzService.update(quizz);
			mv.setViewName("redirect:/quizzes/lister");
		}
		return mv;
	}

	@PostMapping("/supprimer")
	public String supprimer(@RequestParam("id") Long id) {
		if (quizzService.lister() != null && !quizzService.lister().isEmpty()) {
			Quizz quizzToRemove = null;
			List<Quizz> quizzList = quizzService.lister();
			int i = 0;
			while (i < quizzList.size() && quizzToRemove == null) {
				Quizz currentQuizz = quizzList.get(i);
				if (currentQuizz.getId().equals(id)) {
					quizzToRemove = currentQuizz;
				}
				i++;
			}
			if (quizzToRemove != null) {
				quizzService.delete(quizzToRemove);
			}
		}
		return "redirect:/quizzes/lister";
	}

}