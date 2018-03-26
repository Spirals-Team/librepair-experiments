package com.app.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.app.utils.LatLng;
import com.app.validator.AgentInfoValidator;

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
		
		this.agent = agentService.findById( agent.getId() );
		return "redirect:/create/" + agent.getId();
	}

	@RequestMapping(value = "/create/{id}")
	public String create(Model model, @PathVariable("id") Long id) {
		Agent agentInfo = agentService.findById(String.valueOf(id));
		Incident i = new Incident();
		i.setAgent(agentInfo);
		model.addAttribute("incident", i);
		model.addAttribute("topics", topicsService.getTopics());
		return "create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createPost(Model model, @ModelAttribute Incident incident) {
		parseAditionalProperties(incident);
		parseLocation(incident);
		incident.setAgent(this.agent);
		incident.setDate(new Date());
		kafkaIncidentProducer.send(incident);
		return "send";

	}

	private void parseLocation(Incident incident) {

		if (incident.getLocationString() != null && !incident.getLocationString().isEmpty()) {
			String[] location = incident.getLocationString().trim().split(",");
			incident.setLocation(new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1])));
		}

	}

	private void parseAditionalProperties(Incident incident) {
		if (incident.getAditionalPropertiesString() != null && !incident.getAditionalProperties().isEmpty()) {
			String[] aditionalProperties = incident.getAditionalPropertiesString().trim().split(",");
			Map<String, String> result = new HashMap<String, String>();
			for (int i = 0; i < aditionalProperties.length; i++) {
				String[] pv = aditionalProperties[i].split("/");
				result.put(pv[0], pv[1]);
			}
			incident.setAditionalProperties(result);
		} else {
			incident.setAditionalProperties(new HashMap<String, String>());

		}

	}

	@RequestMapping(value = "/send")
	public String send(Model model) {
		return "send";
	}

}