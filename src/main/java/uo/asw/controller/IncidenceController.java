package uo.asw.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uo.asw.entities.Agent;
import uo.asw.entities.Incidence;
import uo.asw.entities.TipoIncidencia;
import uo.asw.services.AgentsService;
import uo.asw.services.IncidenceService;

@Controller
public class IncidenceController {

	@Autowired
	private IncidenceService incidenceService;

	@Autowired
	private AgentsService agentsService;

	@RequestMapping(value = "/sendIncidence", method = RequestMethod.GET)
	public String createIncidenceGet(Principal agente, Model model) {
		Agent agent = agentsService.getAgent(agente.getName());
		model.addAttribute(agent);
		return "sendIncidence";
	}
	
	@RequestMapping(value = "/incidenceSent", method = RequestMethod.GET)
	public String incidenceSent()
	{
		return "incidenceSent";
	}

	@RequestMapping(value = "/incidence/add", method = RequestMethod.POST)
	public String createIncidence(@RequestParam String name, @RequestParam String description, @RequestParam String tags,
			@RequestParam String tipos, @RequestParam String latitud, @RequestParam String longitud,@RequestParam String valores,Principal agente) 
	{
		TipoIncidencia tipoI = Incidence.tipos.stream().filter(x -> x.toString().equals(tipos)).findFirst().get();
		double valor = Double.parseDouble(valores);
		Incidence incidence = new Incidence(name, description, agente.getName(), obtainTagsList(tags), tipoI, valor);
		incidence.setLatitud(Double.parseDouble(latitud));
		incidence.setLongitud(Double.parseDouble(longitud));
		incidenceService.addIncidence(incidence);
		return "redirect:/incidenceSent";
	}

	private List<String> obtainTagsList(String str) {
		List<String> etiquetas = new ArrayList<>();
		Arrays.asList(str.split(",")).forEach(x -> {
			x.replace(" ", "");
			etiquetas.add(x.toLowerCase());
		});
		return etiquetas;
	}
}
