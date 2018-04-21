package com.uniovi.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.uniovi.model.Estado;
import com.uniovi.model.Incidence;
import com.uniovi.services.IncidencesService;

@Controller
public class InciDashboardController {

    @Autowired
    IncidencesService incidenceService;

    public List<SseEmitter> emitters = Collections.synchronizedList(new ArrayList<SseEmitter>());

    @RequestMapping("/listIncidences")
    public String showInfo(Model model) {
	List<Incidence> incidencias = incidenceService.getIncidences();
	model.addAttribute("incidencesList", incidencias);
	return "listIncidences";
    }

    @RequestMapping("/getEmitter")
    public SseEmitter getEmitter() {
	return nuevoEmitter();
    }

    public SseEmitter nuevoEmitter() {
	SseEmitter emitter = new SseEmitter(0L);

	emitter.onTimeout(() -> emitters.remove(emitter));
	emitter.onCompletion(() -> emitters.remove(emitter));

	emitters.add(emitter);

	return emitter;
    }

    @RequestMapping("/inciDashboard/detailsIncidence/{id}")
    public String getIncidenceInfo(Model model, @PathVariable Long id) {

	model.addAttribute("incidence", incidenceService.getIncidence(id));

	return "detailsIncidence";
    }

    @RequestMapping("/inciDashboard/modifyStateIncidence/{id}")
    public String modifyInfo(Model model, @PathVariable Long id) {

	model.addAttribute("incidence", incidenceService.getIncidence(id));
	model.addAttribute("listStates", Estado.values());

	/** COMPLETAR: CREAR EL HTML CORRESPONDIENTE **/
	return "";
    }

    @RequestMapping("/inciDashboard/modifyStateIncidence/{id}/{state}")
    public String modifyState(@PathVariable Long id, @PathVariable String state) {

	incidenceService.modifyState(id, state);

	return "redirect:listIncidences";
    }

    @RequestMapping("/login")
    public String login() {
	return "login";
    }

}
