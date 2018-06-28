package inciManager.incidenceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import inciManager.entities.Agente;
import inciManager.entities.Estado;
import inciManager.entities.Incidencia;
import inciManager.entities.Operator;
import inciManager.inciRepository.InciRepositoryFacade;
import inciManager.kafka.SendIncidence;

@Component
public class IncidenceServiceFacadeClass implements IncidenceServiceFacade {

	@Autowired
	private InciRepositoryFacade repository;

	@Autowired
	private SendIncidence kafka;

	@Override
	public Incidencia processIncidence(Incidencia incidencia) {
		if (incidencia == null)
			return null;

		Agente agente = incidencia.getAgenteAux();
		Incidencia saved = null;

		incidencia.setEstado(Estado.ABIERTA);
		incidencia.setIdentificadorAgente(agente.getIdentificador());

		Operator operadorConMenosTrabajo = getOperadorConMenosTrabajo(repository.getOperators());
		incidencia.setOperadorAsignado(operadorConMenosTrabajo);

		if (!"sensor".equals(agente.getKind()) && !"automatico".equals(agente.getKind()))
			saved = repository.saveIncidence(incidencia);

		kafka.sendIncidence(incidencia);

		return saved == null ? incidencia : saved;
	}

	@Override
	public List<Incidencia> getIncidenceInfo(Agente agente) {
		if (agente == null)
			return null;

		return repository.getIncidence(agente.getIdentificador());
	}

	private Operator getOperadorConMenosTrabajo(List<Operator> operators) {
		Operator menosTrabajo = null;

		if (operators.size() != 0)
			menosTrabajo = operators.get(0);

		for (Operator operator : operators)
			if (operator.getIncidenciasAsignadas().size() < menosTrabajo.getIncidenciasAsignadas().size())
				menosTrabajo = operator;

		return menosTrabajo;
	}

}
