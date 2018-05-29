package dev.paie.web.controller;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.paie.entite.Entreprise;
import dev.paie.entite.Grade;
import dev.paie.entite.ProfilRemuneration;
import dev.paie.entite.RemunerationEmploye;
import dev.paie.repository.EntrepriseRepository;
import dev.paie.repository.GradeRepository;
import dev.paie.repository.ProfilRemunerationRepository;
import dev.paie.repository.RemunerationEmployeRepository;

@Controller
@RequestMapping("/employes")
public class RemunerationEmployeController {

	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private EntrepriseRepository entrepriseRepository;

	@Autowired
	private ProfilRemunerationRepository profilRepository;

	@Autowired
	private RemunerationEmployeRepository remunerationEmployeRepository;

	@RequestMapping(method = RequestMethod.GET, path = "/creer")
	public ModelAndView creerEmploye(Model model) {

		RemunerationEmploye remunerationEmploye = new RemunerationEmploye();
		model.addAttribute(remunerationEmploye);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("employes/creerEmploye");

		List<Grade> listeGrades = gradeRepository.findAll();
		List<Entreprise> listeEntr = entrepriseRepository.findAll();
		List<ProfilRemuneration> listeProfils = profilRepository.findAll();

		mv.addObject("listeGrades", listeGrades);
		mv.addObject("listeEntr", listeEntr);
		mv.addObject("listeProfils", listeProfils);

		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/creer")
	public String submitForm(@ModelAttribute("remunerationEmploye") RemunerationEmploye rem) {
		rem.setDateCreation(ZonedDateTime.now());
		remunerationEmployeRepository.save(rem);
		return "redirect:/mvc/employes/lister";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/lister")
	public ModelAndView listerEmploye() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("employes/listerEmploye");
		mv.addObject("remunerationEmploye", remunerationEmployeRepository.findAll());

		return mv;
	}
}
