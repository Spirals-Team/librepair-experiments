package uo.asw.controller;


import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uo.asw.entities.Agent;
import uo.asw.services.AgentsService;
import uo.asw.services.SecurityService;

@Controller
public class AgentController {

	@Autowired
	AgentsService agentsService;
	
	@Autowired
	SecurityService securityService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String inicalicerLogin(Model model) {
		return "index";
	}

	@RequestMapping(value = "/userlogin", method = RequestMethod.GET)
	public String login(Model model) {
		return "userlogin";
	}

	@RequestMapping(value = "/userlogin", method = RequestMethod.POST)
	public String loginPost(@RequestParam String email, @RequestParam String password, @RequestParam String type) {
		if (!agentsService.comprobarAgente(email,password,type))
			return "redirect:/userlogin?error";
// Cuando se despliegue, descomentar esto y borrar las 3 lineas siguientes
		//Agent agent = agentsService.getAgent(email);
		//if (agent == null || !agent.getKind().equals(type))
		//	return "redirect:/userlogin?error";
		try {
			securityService.autoLogin(email, password);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/userlogin?error";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/userLogout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String perfilUsuario(Principal agente, Model model) {
		Agent agent = agentsService.getAgent(agente.getName());
		model.addAttribute(agent);
		return "profile";
	}
	
	@PostMapping(value="/profile/cambiarContraseña")
	public String cambiarContraseña(Model model, @ModelAttribute Agent agente, @RequestParam String email, @RequestParam String password)
	{
		Agent original=agentsService.getAgent(email);
		original.setPassword(password);
		agentsService.cambiarUsuario(original);
		return "redirect:/";
				
	}
	
}
