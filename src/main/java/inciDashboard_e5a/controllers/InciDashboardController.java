package inciDashboard_e5a.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import inciDashboard_e5a.model.Incidence;
import inciDashboard_e5a.model.Incidence.Estado;
import inciDashboard_e5a.services.IncidencesService;

/**
 * Controlador principal.
 * 
 * @author Tania Álvarez Díaz
 *
 */
@Controller
public class InciDashboardController {

    @Autowired
    IncidencesService incidenceService;

    public List<SseEmitter> emitters = Collections.synchronizedList(new ArrayList<SseEmitter>());

    @RequestMapping("/listIncidences")
    public String showInfo(Model model) {
	List<Incidence> incidencias = incidenceService.getIncidences();
	model.addAttribute("incidencesList", incidencias);
	return "/incidences/listIncidences";
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

    @RequestMapping("/incidence/detailsIncidence/{id}")
    public String getIncidenceInfo(Model model, @PathVariable Long id) {

	model.addAttribute("incidence", incidenceService.getIncidence(id));

	return "/incidences/detailsIncidence";
    }

    @RequestMapping("/incidence/modify/{id}")
    public String modifyInfo(Model model, @PathVariable Long id) {

	model.addAttribute("incidence", incidenceService.getIncidence(id));
	model.addAttribute("listStates", Estado.values());

	return "/incidences/modifyIncidence";
    }

    /**
     * Para guardar los datos modificados
     * 
     * @return
     */
    @RequestMapping(value = "/incidence/modify/{id}", method = RequestMethod.POST)
    public String setModifyInfo(Model model, @PathVariable Long id, @ModelAttribute Incidence incidence,
	    @RequestParam String newState) {
	Incidence original = incidenceService.getIncidence(id);
	// Modificar solo estado
	original.setEstado(Estado.valueOf(newState));
	incidenceService.modifyState(original);
	return "redirect:/incidence/detailsIncidence/" + id;
    }

    @RequestMapping("/login")
    public String login() {
	return "login";
    }

    @RequestMapping("/verMapa/{latitud}/{longitud}")
    public String getMap(Model model, @PathVariable double latitud, @PathVariable double longitud) {
	model.addAttribute("latitud", latitud);
	model.addAttribute("longitud", longitud);
	return "incidences/map";
    }

}
