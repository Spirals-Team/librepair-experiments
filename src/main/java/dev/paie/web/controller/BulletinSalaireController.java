package dev.paie.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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

	@Autowired
	BulletinSalaireRepository bsr;

	@Autowired
	RemunerationEmployeRepository rer;

	@Autowired
	PeriodeRepository per;

	@RequestMapping(method = RequestMethod.GET, path = "/creer")
	@Secured("ROLE_ADMINISTRATEUR")
	public ModelAndView creerBulletin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("bulletins/creerBulletin");
		mv.addObject("periodes", per.findAll());
		mv.addObject("RemunerationEmployes", rer.findAll());

		mv.addObject("bulletin", new BulletinSalaire());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/creer")
	@Secured("ROLE_ADMINISTRATEUR")
	public ModelAndView creerEmploye(@ModelAttribute("bulletin") BulletinSalaire bulletinSalaire) {
		bsr.save(bulletinSalaire);
		return new ModelAndView("redirect: lister");
	}

}
