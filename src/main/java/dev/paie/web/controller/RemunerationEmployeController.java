package dev.paie.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import dev.paie.entite.Collegue;
import dev.paie.entite.RemunerationEmploye;
import dev.paie.repository.EntrepriseRepository;
import dev.paie.repository.GradeRepository;
import dev.paie.repository.ProfilRemunerationRepository;
import dev.paie.repository.RemunerationEmployeRepository;

@Controller
@RequestMapping("/employes")
public class RemunerationEmployeController {

	private EntrepriseRepository entrepriseRepo;
	private ProfilRemunerationRepository profilRepo;
	private GradeRepository gradeRepo;
	private RemunerationEmployeRepository remuRepo;

	@Autowired
	public RemunerationEmployeController(EntrepriseRepository entrepriseRepo, ProfilRemunerationRepository profilRepo,
			GradeRepository gradeRepo, RemunerationEmployeRepository remuRepo) {
		super();
		this.entrepriseRepo = entrepriseRepo;
		this.profilRepo = profilRepo;
		this.gradeRepo = gradeRepo;
		this.remuRepo = remuRepo;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/creer")
	public ModelAndView creerEmploye() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("employes/creerEmploye");
		mv.addObject("listeEntreprises", entrepriseRepo.findAll());
		mv.addObject("listeProfils", profilRepo.findAll());
		mv.addObject("listeGrades", gradeRepo.findAll());

		mv.addObject("employe", new RemunerationEmploye());

		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/lister")
	public ModelAndView listerEmploye() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("employes", remuRepo.findAll());
		mv.setViewName("employes/listerEmploye");

		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/creer")
	public String setupForm(@ModelAttribute("employe") RemunerationEmploye employe) {
		RestTemplate rt = new RestTemplate();
		Collegue[] result = rt.getForObject("http://collegues-api.cleverapps.io/collegues", Collegue[].class);
		boolean existenceMatricule = false;
		String redirection = "redirect:/mvc/employes/creer";
		for (Collegue collegue : result) {
			if (collegue.getMatricule().equals(employe.getMatricule())) {
				existenceMatricule = true;
				redirection = "redirect:/mvc/employes/lister";
			}
		}
		if (existenceMatricule) {
			this.remuRepo.save(employe);
		}
		return redirection;

	}
}
