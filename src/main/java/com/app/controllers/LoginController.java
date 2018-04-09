package com.app.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.entities.Agent;
import com.app.services.AgentInfoService;
import com.app.validator.AgentInfoValidator;

@Controller
public class LoginController {
	
	@Autowired
	private AgentInfoValidator agentInfoValidator;
	
	@Autowired
	private AgentInfoService agentInfoService;
	
	@RequestMapping(value = {"/", "", "/login"})
	public String login(Model model) {
		model.addAttribute("agent", new Agent());
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Validated Agent agent, BindingResult result, Model model,
			HttpSession session) {

		agentInfoValidator.validate(agent, result);
		if (result.hasErrors() || !agentInfoService.verifyAgent(agent)) {
			return "login";
		}
		
		session.setAttribute("agent", agent);
		
		return "redirect:/create/" + agent.getId();
	}

}
