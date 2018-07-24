package dev.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.entites.Classe;
import dev.entites.Examen;
import dev.entites.Note;
import dev.entites.Quizz;
import dev.entites.Stagiaire;
import dev.metiers.ClasseService;
import dev.metiers.ExamenService;
import dev.metiers.QuizzService;
import dev.metiers.StagiaireService;

@Controller
@RequestMapping("/examens")
public class ExamenController {

	private ExamenService examenService;
	private QuizzService quizzService;
	private ClasseService classeService;

	private StagiaireService stagiaireService;

	public ExamenController(ExamenService examenService, QuizzService quizzService, ClasseService classeService,
			StagiaireService stagiaireService) {
		super();
		this.examenService = examenService;
		this.quizzService = quizzService;
		this.classeService = classeService;
		this.stagiaireService = stagiaireService;

	}

	@GetMapping("/lister")
	public ModelAndView lister() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("examList", examenService.lister());
		mv.setViewName("examens/listerExamens");
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/ajouter")
	public ModelAndView ajouter() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("examen", new Examen());
		mv.addObject("quizzList", quizzService.lister());
		mv.addObject("classeList", classeService.lister());
		mv.setViewName("examens/ajouterExamen");
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/ajouter")
	public String submitForm(@ModelAttribute("examen") Examen exam) {
		for (Quizz q : quizzService.lister()) {
			if (q.getId() == exam.getQuizz().getId()) {
				exam.setQuizz(q);
			}
		}

		for (Classe c : classeService.lister()) {
			if (c.getId() == exam.getClasse().getId()) {
				exam.setClasse(c);
			}
		}
		examenService.ajouter(exam);
		return "redirect:/examens/lister";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editer")
	public ModelAndView editer(@RequestParam("id") Long id) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("examen", examenService.getById(id));
		mv.addObject("note", new Note());
		mv.addObject("quizzList", quizzService.lister());
		mv.addObject("classeList", classeService.lister());
		mv.addObject("noteList", examenService.getById(id).getNotes());
		mv.addObject("listStagiaire", examenService.getById(id).getClasse().getStagiaires());
		mv.setViewName("examens/editerExamen");
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/editer")
	public String submitForm(@ModelAttribute("examen") @Valid Examen exam, BindingResult bindingResult,
			@RequestParam Long id) {
		exam.setId(id);

		if (!bindingResult.hasErrors()) {
			for (Quizz q : quizzService.lister()) {
				if (q.getId() == exam.getQuizz().getId()) {
					exam.setQuizz(q);
				}
			}

			for (Classe c : classeService.lister()) {
				if (c.getId() == exam.getClasse().getId()) {
					exam.setClasse(c);
				}
			}

			if (exam.getClasse().getId().equals(examenService.getById(id).getClasse().getId())) {
				exam.setNotes(examenService.getById(id).getNotes());
			}

			examenService.updateById(exam);
		} else {
			return "redirect:/examens/editer?id=" + id;
		}

		return "redirect:/examens/lister";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/editer/note")
	public ModelAndView submitForm(@ModelAttribute("note") @Valid Note note, BindingResult bindingResult) {

		if (!bindingResult.hasErrors()) {
			for (Stagiaire s : stagiaireService.lister()) {
				if (s.getId().equals(note.getStagiaire().getId())) {
					note.setStagiaire(s);
				}
			}
			examenService.addNote(note);

			return afficherGetEdit(new Note(), note.getExamen().getId());
		} else {
			return afficherGetEdit(note, note.getExamen().getId());
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/supprimer")
	public String supprimer(@RequestParam("id") Long id) {
		if (examenService.lister() != null && examenService.lister().size() > 0) {
			Examen examToDelete = null;
			for (Examen exam : examenService.lister()) {
				if (exam.getId().equals(id)) {
					examToDelete = exam;
				}
			}

			if (examToDelete != null) {
				examenService.supprimerExam(examToDelete);
			}
		}

		return "redirect:/examens/lister";
	}

	private ModelAndView afficherGetEdit(Note note, Long id) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("examen", examenService.getById(id));
		mv.addObject("note", note);
		mv.addObject("quizzList", quizzService.lister());
		mv.addObject("classeList", classeService.lister());
		mv.addObject("noteList", examenService.getById(id).getNotes());
		mv.addObject("listStagiaire", examenService.getById(id).getClasse().getStagiaires());
		mv.setViewName("examens/editerExamen");
		return mv;
	}
}
