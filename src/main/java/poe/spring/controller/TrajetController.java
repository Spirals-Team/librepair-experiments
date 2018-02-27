package poe.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import poe.spring.domain.Trajet;
import poe.spring.exception.DuplicateVoyageBusinessException;
import poe.spring.form.TrajetForm;
import poe.spring.repository.TrajetRepository;
import java.util.List;

import javax.validation.Valid;

@Controller
@RequestMapping("/trajet")
public class TrajetController {
	
    @Autowired
    TrajetRepository trajetRepository;
    
    @GetMapping
    public String list(Model model) {
        List<Trajet> trajets = (List<Trajet>) trajetRepository.findAll();
        model.addAttribute("trajets", trajets);
        return "/trajet/list";
    }

  @PostMapping
  public String save(@Valid TrajetForm form, 
  		BindingResult bindingResult, 
  		RedirectAttributes attr,  
  		Model model) {
  	System.out.println("villeDepart " + form.getVilleDepart());
      System.out.println("villeArrive " + form.getVilleArrivee());
      System.out.println("DateDepart  " + form.getDateDepart());
      System.out.println("Prix        " + form.getPrix());
      System.out.println("NbPlace     " + form.getNbPlace());
      if (bindingResult.hasErrors()) {
          return "trajet/trajet";
      }
    	  Trajet trajet = new Trajet();
    	  trajet.setVilleDepart(form.getVilleDepart());
    	  trajet.setVilleArrivee(form.getVilleArrivee());
    	  trajet.setDateDepart(form.getDateDepart());
    	  
    	  trajetRepository.save(trajet);
    	  
      return "redirect:/trajet/list";
  }
}

