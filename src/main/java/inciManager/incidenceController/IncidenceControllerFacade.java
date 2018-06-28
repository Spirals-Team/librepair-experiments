package inciManager.incidenceController;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import inciManager.entities.Agente;
import inciManager.entities.Incidencia;

public interface IncidenceControllerFacade {

	public String checkIncidence(Agente agente, Model modelo);

	String addIncidence(Incidencia incidence, MultipartFile image);

}
