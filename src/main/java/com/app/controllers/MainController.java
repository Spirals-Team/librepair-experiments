package com.app.controllers;

import javax.servlet.http.HttpSession;
import java.util.Date;

import com.app.services.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.entities.Agent;
import com.app.entities.Incident;
import com.app.producers.KafkaIncidentProducer;
import com.app.services.TopicsService;
import com.app.validator.IncidentValidator;

@Controller
public class MainController {

	@Autowired
	private TopicsService topicsService;

	@Autowired
	private IncidentService incidentService;

	@Autowired
	private KafkaIncidentProducer kafkaIncidentProducer;
	
	@Autowired
	private IncidentValidator incidentValidator;


	@RequestMapping(value = "/create/{id}")
	public String create(Model model, @PathVariable("id") Long id, HttpSession session) {
		Agent agentInfo = (Agent) session.getAttribute("agent");
		if(agentInfo != null) {
			Incident i = new Incident();
			i.setAgent(agentInfo.getIdautogenerado());
			model.addAttribute("incident", i);
			model.addAttribute("topics", topicsService.getTopics());
			model.addAttribute("incidentsList",incidentService.getIncidentsByAgent(agentInfo));
			return "create";
		}
		else return "redirect:/login";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createPost(Model model, @ModelAttribute Incident incident,BindingResult result,
			HttpSession session) {
		incidentValidator.validate(incident, result);
		if (result.hasErrors()) {
			model.addAttribute("topics", topicsService.getTopics());
			return "create";
		}
		incident.setAgent(((Agent)session.getAttribute("agent")).getIdautogenerado());
		incident.setDate(new Date());
		incidentService.saveIncident(incident);
		kafkaIncidentProducer.send(incident);
		return "send";

	}

	@RequestMapping(value = "/send")
	public String send(Model model, HttpSession session) {
		Agent agent = (Agent) session.getAttribute("agent");
		if(agent != null) return "send";
		else return "redirect:/login";
	}

}