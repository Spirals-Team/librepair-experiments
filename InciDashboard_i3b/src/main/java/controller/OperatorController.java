package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import model.Incidence;
import model.Operator;
import repository.IncidenceRepository;
import utils.IncidenceUtils;

@Controller
public class OperatorController {
	
	public static Operator loggedOperator = null;
	
	@Autowired
	private IncidenceRepository repo;
	
	
	@RequestMapping("/operatorpanel")
	public String operatorPanel(Model model) {
		if(loggedOperator==null)
			return "redirect:index";
		List<Incidence> incis = repo.findAll();
		model.addAttribute("incidences", incis);
		model.addAttribute("operator", loggedOperator);
		List<Incidence> assignedIncis = IncidenceUtils.filterByOperator(incis, loggedOperator.getOperatorId());
		model.addAttribute("assignedIncis", assignedIncis);
		return "operator";
	}
	
}
