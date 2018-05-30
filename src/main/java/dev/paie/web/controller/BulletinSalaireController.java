package dev.paie.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.paie.entite.BulletinSalaire;
import dev.paie.repository.BulletinSalaireRepository;
import dev.paie.repository.PeriodeRepository;
import dev.paie.repository.RemunerationEmployeRepository;

@Controller
@RequestMapping("/bulletins")
public class BulletinSalaireController {
	private PeriodeRepository periodeRepo;
	private RemunerationEmployeRepository remuRepo;
	private BulletinSalaireRepository bulletinRepo;

	@Autowired
	public BulletinSalaireController(PeriodeRepository periodeRepo, RemunerationEmployeRepository remuRepo,
			BulletinSalaireRepository bulletinRepo) {
		super();
		this.bulletinRepo = bulletinRepo;
		this.periodeRepo = periodeRepo;
		this.remuRepo = remuRepo;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/creer")
	public ModelAndView creerBulletinSalaire() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("bulletins/creerBulletin");
		mv.addObject("listePeriodes", periodeRepo.findAll());
		mv.addObject("listeMatricules", remuRepo.findAll());

		mv.addObject("bulletinSalaire", new BulletinSalaire());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/creer")
	public String setupForm(@ModelAttribute("bulletinSalaire") BulletinSalaire bulletinSalaire) {
		this.bulletinRepo.save(bulletinSalaire);
		return "redirect:/mvc/bulletins/creer";
	}
}
