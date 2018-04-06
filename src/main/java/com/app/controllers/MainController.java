package com.app.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.entities.Agent;
import com.app.entities.Incident;
import com.app.producers.KafkaIncidentProducer;
import com.app.services.AgentService;
import com.app.services.TopicsService;
import com.app.validator.AgentInfoValidator;
import com.app.validator.IncidentValidator;

@Controller
public class MainController {

	@Autowired
	private AgentInfoValidator agentValidator;

	@Autowired
	private AgentService agentService;

	@Autowired
	private TopicsService topicsService;

	@Autowired
	private KafkaIncidentProducer kafkaIncidentProducer;
	
	@Autowired
	private IncidentValidator incidentValidator;

	private Agent agent;

	@RequestMapping(value = { "/login", "/", "" }, method = RequestMethod.GET)
	public String getLogin(Model model) {
		model.addAttribute("agent", new Agent());
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Validated Agent agent, BindingResult result, Model model) {

		agentValidator.validate(agent, result);
		if (result.hasErrors()) {
			return "login";
		}

		this.agent = agentService.findById(agent.getId());
		return "redirect:/create/" + agent.getId();
	}

	@RequestMapping(value = "/create/{id}")
	public String create(Model model, @PathVariable("id") Long id) {
		if(this.agent != null)
		{
			Agent agentInfo = agentService.findById(String.valueOf(id));
			Incident i = new Incident();
			i.setAgent(agentInfo);
			model.addAttribute("incident", i);
			model.addAttribute("topics", topicsService.getTopics());
			return "create";
		}
		else return "redirect:/login";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createPost(Model model, @ModelAttribute Incident incident,BindingResult result) {
		incidentValidator.validate(incident, result);
		if (result.hasErrors()) {
			return "redirect:/create/"+this.agent.getId();
		}
		incident.setAgent(this.agent);
		incident.setDate(new Date());
		kafkaIncidentProducer.send(incident);
		return "send";

	}

	@RequestMapping(value = "/send")
	public String send(Model model) {
		if(this.agent != null) return "send";
		else return "redirect:/login";
	}

}