package dev.paie.web.controller;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import dev.paie.entite.Collegue;
import dev.paie.entite.Entreprise;
import dev.paie.entite.Grade;
import dev.paie.entite.ProfilRemuneration;
import dev.paie.entite.RemunerationEmploye;
import dev.paie.repository.CollegueRepository;
import dev.paie.repository.EntrepriseRepository;
import dev.paie.repository.GradeRepository;
import dev.paie.repository.ProfilRemunerationRepository;
import dev.paie.repository.RemunerationEmployeRepository;

@Controller
@RequestMapping("/employes")
public class RemunerationEmployeController {

	@Autowired
	EntrepriseRepository er;

	@Autowired
	ProfilRemunerationRepository prr;

	@Autowired
	GradeRepository gr;

	@Autowired
	RemunerationEmployeRepository rer;

	@Autowired
	CollegueRepository cr;

	@RequestMapping(method = RequestMethod.GET, path = "/creer")
	@Secured("ROLE_ADMINISTRATEUR")
	public ModelAndView creerEmploye() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("employes/creerEmploye");

		RestTemplate rt = new RestTemplate();
		Collegue[] toutEmployes = rt.getForObject("http://collegues-api.cleverapps.io/collegues", Collegue[].class);
		List<Collegue> matricules = new LinkedList<>();
		for (int i = 0; i < toutEmployes.length; i++) {
			matricules.add(toutEmployes[i]);
		}
		mv.addObject("collegues", matricules);

		List<Entreprise> entreprises = er.findAll();
		mv.addObject("entreprises", entreprises);

		List<ProfilRemuneration> profileRemunerations = prr.findAll();
		mv.addObject("profil_remunerations", profileRemunerations);

		List<Grade> grades = gr.findAll();
		mv.addObject("grades", grades);

		mv.addObject("employe", new RemunerationEmploye());

		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/creer")
	@Secured("ROLE_ADMINISTRATEUR")
	public String creerEmployeP(@ModelAttribute("employe") RemunerationEmploye reEmploye) {
		reEmploye.setDate(ZonedDateTime.now());
		rer.save(reEmploye);
		return "redirect:/mvc/employes/lister";

	}

	@RequestMapping(method = RequestMethod.GET, path = "/lister")
	@Secured({ "ROLE_UTILISATEUR", "ROLE_ADMINISTRATEUR" })
	public ModelAndView listerEmployes() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("employes/listerEmployes");

		List<RemunerationEmploye> remEmp = rer.findAll();
		mv.addObject("employes", remEmp);

		return mv;
	}

}