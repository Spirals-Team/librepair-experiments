package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import model.Incidence;
import repository.IncidenceRepository;

@Controller
@RequestMapping("/manage")
public class ManageController {
	
	public static final String MANAGE_VIEW = "manage";
	public static final String RESULT_VIEW = "result";

	public static  Incidence lastIncidence=null;
	
	@Autowired
	private IncidenceRepository repo;
	
 	@RequestMapping(value="/{id}")
    public String manage(Model model, @PathVariable("id") String id) {
    	Incidence aux = repo.findOne(id);
    	lastIncidence = aux;
    	model.addAttribute("inci", aux);
    	return MANAGE_VIEW;
    }
	 
	 @PostMapping("/addIncidence")
	 public ModelAndView addIncidence(@ModelAttribute ("inci") Incidence inci) {
		 repo.delete(lastIncidence);
		 List<String> comments = lastIncidence.getComments();
		 comments.add(inci.getComment());
		 lastIncidence.setComments(comments); 
		 lastIncidence.setState(inci.getState());
		 repo.insert(lastIncidence);
		 
		 String url = "redirect:/manage/" + lastIncidence.getInciId();
		 return new ModelAndView(url);
	 }

	 
	 @PostMapping("/return")
	 public ModelAndView returnDashboard() {
		 return new ModelAndView("redirect:/operatorpanel");
	 }
}
