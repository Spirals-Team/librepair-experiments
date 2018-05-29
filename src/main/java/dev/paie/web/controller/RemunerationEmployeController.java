package dev.paie.web.controller;

import dev.paie.entite.RemunerationEmploye;
import dev.paie.repository.EntrepriseRepository;
import dev.paie.repository.GradeRepository;
import dev.paie.repository.ProfilRemunerationRepository;
import dev.paie.repository.RemunerationEmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.ZonedDateTime;

@Controller
@RequestMapping("/employes")
public class RemunerationEmployeController {
	
	@Autowired GradeRepository gradeRepository;
	@Autowired ProfilRemunerationRepository profilRepository;
	@Autowired EntrepriseRepository entrepriseRepository;
	
	@Autowired RemunerationEmployeRepository remunerationEmployeRepository;
	
   /* @RequestMapping(method = RequestMethod.GET, path = "/creer")
    public ModelAndView creerEmploye() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("employes/creerEmploye");
        mv.addObject("prefixMatricule","M00");
        mv.addObject("grade",gradeRepository.findAll());
        mv.addObject("entreprise",entrepriseRepository.findAll());
        mv.addObject("profilRemuneration",profilRepository.findAll());
        return mv;
    }
    */
	@RequestMapping(method = RequestMethod.GET, path = "/lister")
    public String setupFormList(Model model) {
        //Liaison du modèle et de l'objet.
		model.addAttribute("employes", remunerationEmployeRepository.findAll());
		System.out.println(remunerationEmployeRepository.findAll().size());
        //Renvoi du nom logique de la vue formulaire.
        return "employes/listEmploye";
    }
    

    
	
	@RequestMapping(method = RequestMethod.GET, path = "/creer")
    public String setupForm(Model model) {
        //Liaison du modèle et de l'objet.
    	model.addAttribute("employe", new RemunerationEmploye());
        model.addAttribute("entreprise",entrepriseRepository.findAll());
        model.addAttribute("grade",gradeRepository.findAll());
        model.addAttribute("profilRemuneration",profilRepository.findAll());
        //Renvoi du nom logique de la vue formulaire.
        return "employes/creerEmploye";
    }
    
    @RequestMapping( method = RequestMethod.POST , path ="/creer")
    public String submitForm(@ModelAttribute("employe") RemunerationEmploye employe) {
    	employe.setDateCreation(ZonedDateTime.now());
    	remunerationEmployeRepository.save(employe);
        return "redirect:/mvc/employes/lister";
    }
}