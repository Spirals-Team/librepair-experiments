package dev.paie.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.paie.entite.RemunerationEmploye;
import dev.paie.repository.EntrepriseRepository;
import dev.paie.repository.GradeRepository;
import dev.paie.repository.ProfilRepository;
import dev.paie.repository.RemunerationEmployerRepository;

@Controller
@RequestMapping("/employes")
public class RemunerationEmployerController {

	private EntrepriseRepository repoEnt;
	private ProfilRepository repoProfil;
	private GradeRepository repoGrade;
	private RemunerationEmployerRepository repoRemu;

	@Autowired
	public RemunerationEmployerController(EntrepriseRepository repoEnt, ProfilRepository repoProfil,
			GradeRepository repoGrade, RemunerationEmployerRepository repoRemu) {
		super();
		this.repoEnt = repoEnt;
		this.repoProfil = repoProfil;
		this.repoGrade = repoGrade;
		this.repoRemu = repoRemu;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/creer")
	public ModelAndView creerEmploye() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("employes/creerEmploye");

		mv.addObject("employer", new RemunerationEmploye());
		mv.addObject("listeEntreprise", repoEnt.findAll());
		mv.addObject("listeProfil", repoProfil.findAll());
		mv.addObject("listeGrade", repoGrade.findAll());
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/lister")
	public ModelAndView listeEmployer() {
		ModelAndView mv = new ModelAndView();

		mv.addObject("listeRemu", repoRemu.findAll());
		mv.setViewName("employes/listerEmploye");
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/creer")
	public ModelAndView setupForm(@ModelAttribute("employer") RemunerationEmploye employe) {
		/*
		 * RestTemplate rt = new RestTemplate(); Collegue result =
		 * rt.getForObject(
		 * "http://collegues-api.cleverapps.io/collegues?matricule={matricule}",
		 * Collegue.class, employe.getMatricule()); if (result != null) {
		 */
		this.repoRemu.save(employe);
		// }
		return listeEmployer();
	}
}