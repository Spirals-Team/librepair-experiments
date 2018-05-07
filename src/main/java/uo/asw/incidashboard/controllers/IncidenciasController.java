package uo.asw.incidashboard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IncidenciasController {
	
	@RequestMapping("/inci/cargar")
	public String cargarIncidencias(Model model) {
		return "redirect:/home";
	}
}
