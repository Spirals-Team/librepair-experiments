package dev.paie.web.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.paie.entite.RemunerationEmploye;
import dev.paie.repository.EntrepriseRepository;
import dev.paie.repository.GradeRepository;
import dev.paie.repository.ProfilRepository;
import dev.paie.repository.RemunerationEmployeRepository;

@Controller
@RequestMapping("/employes")
public class RemunerationEmployeController {

	@Autowired
	EntrepriseRepository er;

	@Autowired
	ProfilRepository pr;

	@Autowired
	GradeRepository gr;

	@Autowired
	RemunerationEmployeRepository rer;

	@RequestMapping(method = RequestMethod.GET, path = "/creer")
	@Secured("ROLE_ADMINISTRATEUR")
	public ModelAndView creerEmploye() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("employes/creerEmploye");
		mv.addObject("entreprises", er.findAll());
		mv.addObject("profilRemunerations", pr.findAll());
		mv.addObject("grades", gr.findAll());

		mv.addObject("employe", new RemunerationEmploye());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/creer")
	@Secured("ROLE_ADMINISTRATEUR")
	public ModelAndView creerEmploye(@ModelAttribute("employe") RemunerationEmploye remunerationEmploye) {
		remunerationEmploye.setDateDeCreation(LocalDateTime.now());
		rer.save(remunerationEmploye);
		return new ModelAndView("redirect: lister");
	}

	@RequestMapping(method = RequestMethod.GET, path = "/lister")
	@Secured({ "ROLE_UTILISATEUR", "ROLE_ADMINISTRATEUR" })
	public ModelAndView listerEmployes() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("employes/listerEmployes");
		mv.addObject("employes", rer.findAll());
		return mv;
	}

}