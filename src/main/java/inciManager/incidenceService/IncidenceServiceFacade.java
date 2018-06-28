package inciManager.incidenceService;

import java.util.List;

import inciManager.entities.Agente;
import inciManager.entities.Incidencia;

public interface IncidenceServiceFacade {

	public Incidencia processIncidence(Incidencia incidencia);

	public List<Incidencia> getIncidenceInfo(Agente agente);

}
