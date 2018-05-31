package dev.paie.web.controller;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.Periode;
import dev.paie.entite.RemunerationEmploye;
import dev.paie.repository.BulletinSalaireRepository;
import dev.paie.repository.PeriodeRepository;
import dev.paie.repository.RemunerationEmployeRepository;
import dev.paie.service.CalculerRemunerationService;

@Controller
@RequestMapping("/bulletins")
public class BulletinSalaireController {

	@Autowired
	RemunerationEmployeRepository rer;

	@Autowired
	PeriodeRepository pr;

	@Autowired
	BulletinSalaireRepository bsr;

	@Autowired
	CalculerRemunerationService crs;

	@RequestMapping(method = RequestMethod.GET, path = "/creer")
	public ModelAndView creerBulletin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/bulletins/creerBulletin");

		List<Periode> periodes = pr.findAll();
		mv.addObject("periodes", periodes);

		List<RemunerationEmploye> remunerationEmployes = rer.findAll();
		mv.addObject("remuneration_employes", remunerationEmployes);

		mv.addObject("bulletin", new BulletinSalaire());

		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/creer")
	public String creerBulletinP(@ModelAttribute("bulletin") BulletinSalaire bulletinSalaire) {
		bulletinSalaire.setDate(ZonedDateTime.now());
		bsr.save(bulletinSalaire);
		return "redirect:/mvc/bulletins/lister";

	}

	@RequestMapping(method = RequestMethod.GET, path = "/lister")
	public ModelAndView listerBulletins() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("bulletins/listerBulletins");

		mv.addObject("fullBulletin", crs.fullBulletin());

		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/visualiser/{id}")
	public ModelAndView visualiserBulletin(@PathVariable Integer id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("bulletins/visualiserBulletin");

		BulletinSalaire bulletin = bsr.findOne(id);
		mv.addObject("bulletin", bulletin);

		// ResultatCalculRemuneration calcul = crs.calculer(bulletin);
		// mv.addObject("calcul", calcul);

		RemunerationEmploye employe = bulletin.getRemunerationEmploye();
		mv.addObject("employe", employe);
		return mv;
	}
}
