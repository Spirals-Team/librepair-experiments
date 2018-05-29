package dev.paie.web.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.paie.entite.Entreprise;
import dev.paie.entite.Grade;
import dev.paie.entite.ProfilRemuneration;
import dev.paie.entite.RemunerationEmploye;
import dev.paie.services.EmployeService;
import dev.paie.services.EntrepriseService;
import dev.paie.services.GradeServiceJdbcTemplate;
import dev.paie.services.ProfilService;

@Controller
@RequestMapping("/employes")
public class RemunerationEmployeController {

	@Autowired
	private EntrepriseService entrepriseService;

	@Autowired
	private ProfilService profilService;

	@Autowired
	private GradeServiceJdbcTemplate gradeServiceJdbcTemplate;

	@Autowired
	private EmployeService employeService;

	@RequestMapping(method = RequestMethod.GET, path = "/creer")
	public ModelAndView creerEmploye() {
		List<Grade> grades = gradeServiceJdbcTemplate.lister();
		List<ProfilRemuneration> profils = profilService.getLesProfil();
		List<Entreprise> lesEntre = entrepriseService.getLesEntreprises();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("employes/creerEmploye");
		mv.addObject("lesEntre", lesEntre);
		mv.addObject("profils", profils);
		mv.addObject("grades", grades);
		mv.addObject("employe", new RemunerationEmploye());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/creer")
	public String submitForm(@ModelAttribute("employe") RemunerationEmploye emp) {
		emp.setDateCreation(LocalDateTime.now());
		employeService.saveEmp(emp);
		return "redirect:/mvc/employes/lister";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/lister")
	public ModelAndView listerEmploye() {
		List<RemunerationEmploye> employes = employeService.getLesEmploye();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("employes/listeEmploye");
		mv.addObject("employes", employes);
		return mv;
	}

}