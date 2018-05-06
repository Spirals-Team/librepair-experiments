package uniovi.es.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uniovi.es.entities.Agent;
import uniovi.es.services.AgentsService;

@Controller
public class MainController {
	
	@Autowired
    private AgentsService agentsService;

    @RequestMapping(value = "/checkAgentInfo", method = RequestMethod.GET)
    public String login() {
        return "checkAgent";

    }

    @RequestMapping(value = "/checkAgentInfo", method = RequestMethod.POST)
    public String loginPost(Model model, @RequestParam(value = "login") String login, 
    		@RequestParam(value = "password") String password, 
    		@RequestParam(value = "kind") String kindCode ) {

        Agent user = agentsService.getAgent(login, password, kindCode);

        if (user == null)
        {
        	model.addAttribute("errorDetails", true);
            return "checkAgent";
        }
        else {
		    model.addAttribute("name", user.getName());
		    model.addAttribute("email", user.getEmail());
		    model.addAttribute("location", user.getLocation());
		    model.addAttribute("kind", user.getKind());
		    model.addAttribute("kindCode", user.getKindCode());
		
		    return "infoAgent";
        }

    }
}
