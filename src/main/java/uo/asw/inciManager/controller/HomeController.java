package uo.asw.inciManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import uo.asw.inciManager.service.AgentService;
import uo.asw.inciManager.service.ValorLimiteService;

@Controller
public class HomeController {

	@Autowired
	private ValorLimiteService valorLimiteService;
	
	@Autowired
	private AgentService agentService;
	
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("idAgente", null);
		model.addAttribute("mensajesList", agentService.getMensajesChatBot());
		
		model.addAttribute("valoresList", valorLimiteService.findAll());
		
		return comprobarConectado("home");
	}
	private String comprobarConectado(String destino) {
		if(agentService.getIdConnected() == null) {
			return "redirect:/login";
		}else return destino;
	}
}
