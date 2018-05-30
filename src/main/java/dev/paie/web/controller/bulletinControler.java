package dev.paie.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.paie.entite.BulletinSalaire;
import dev.paie.repository.PeriodeRepository;
import dev.paie.repository.RemunerationEmployerRepository;

@Controller
@RequestMapping("/bulletins")
public class bulletinControler {

	private PeriodeRepository repoPeriode;
	private RemunerationEmployerRepository repoRemu;

	@Autowired
	public bulletinControler(PeriodeRepository repoPeriode, RemunerationEmployerRepository repoRemu) {
		super();
		this.repoPeriode = repoPeriode;
		this.repoRemu = repoRemu;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/creer")
	public ModelAndView creerBulletin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("bulletins/creerBuletinSalaire");

		mv.addObject("listePeriode", repoPeriode.findAll());
		mv.addObject("listeMatri", repoRemu.findAll());
		mv.addObject("bulletin", new BulletinSalaire());

		return mv;
	}

}
