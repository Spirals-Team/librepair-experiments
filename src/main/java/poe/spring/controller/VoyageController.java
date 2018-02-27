//package poe.spring.controller;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import poe.spring.delegate.VoyageCreationDelegate;
//import poe.spring.domain.Voyage;
//import poe.spring.exception.DuplicateVoyageBusinessException;
//import poe.spring.form.VoyageForm;
//import poe.spring.service.VoyageManagerService;
//
//@Controller
//@RequestMapping("/voyage")
//public class VoyageController {
//	
//	@Autowired
//    VoyageManagerService voyageManagerService;
//
//    @GetMapping
//    public String index(VoyageForm form) {
//        return "voyage"; // affiche voyage.html
//    }
//
//    @PostMapping
//    public String save(@Valid VoyageForm form, 
//    		BindingResult bindingResult, 
//    		RedirectAttributes attr,  
//    		Model model) {
//    	System.out.println("villeDepart " + form.getVilleDepart());
//        System.out.println("villeArrive " + form.getVilleArrive());
//        System.out.println("DateDepart  " + form.getDateDepart());
//        System.out.println("Prix        " + form.getPrix());
//        System.out.println("NbPlace     " + form.getNbPlace());
//        if (bindingResult.hasErrors()) {
//            return "voyage";
//        }
//        try {
//            voyageManagerService.voyage(
//            		form.getVilleDepart(), 
//            		form.getVilleArrive(), 
//            		form.getDateDepart(), 
//            		form.getPrix(), 
//            		form.getNbPlace());
//        } catch (DuplicateVoyageBusinessException e) {
//            model.addAttribute("error", "Ce login est déjà utilisé!");
//            return "voyage";
//        }
//        attr.addAttribute("login", form.getVoyage());
//        return "redirect:/voyage/success";
//    }
//
//    @GetMapping("/success")
//    public String success(@RequestParam("login") String login, Model model) {
//        model.addAttribute("login", login);
//        return "success"; // success.html
//    }
//}