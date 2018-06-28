package inciManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import inciManager.entities.Agente;
import inciManager.incidenceController.IncidenceControllerFacade;

@Controller
public class WebClient {

	@Autowired
	private IncidenceControllerFacade incidenceController;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/login")
	public String logInGet() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String logInPost(@ModelAttribute Agente agente, Model modelo, @RequestParam String operation) {
		modelo.addAttribute("agente", agente);

		if ("add".equals(operation))
			return "incidence/add";
		if ("view".equals(operation))
			return incidenceController.checkIncidence(agente, modelo);
		else
			return "error";
	}

}
