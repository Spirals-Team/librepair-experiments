package asw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import asw.entities.Status;
import asw.services.EtiquetaService;
import asw.services.IncidenceService;
import asw.services.OperadorService;

@Controller
public class EstadisticasController {
	@Autowired
	private IncidenceService inciService;
	
	@Autowired
	private OperadorService operaService;
	
	@Autowired
	private EtiquetaService etiquetaService;
	
	@RequestMapping("/estadisticas")
	public String getStatistics(Model model) {
		model.addAttribute("numeroTotalIncidencias", inciService.getIncidences().size());
		model.addAttribute("numeroTotalIncidenciasAbiertas", inciService.cantidadIncidenciasTipo(Status.ABIERTO));
		model.addAttribute("numeroTotalIncidenciasAnuladas", inciService.cantidadIncidenciasTipo(Status.ANULADA));
		model.addAttribute("numeroTotalIncidenciasCerradas", inciService.cantidadIncidenciasTipo(Status.CERRADO));
		model.addAttribute("numeroTotalIncidenciasEnProceso", inciService.cantidadIncidenciasTipo(Status.EN_PROCESO));
		model.addAttribute("operadorConMasIncidenciasHistoricas", operaService.findOperatorWithMoreIncidnces());
		model.addAttribute("etiquetaMasUsada", etiquetaService.etiquetaMasUsada());
		
		return "estadisticas";
	}

	
}
