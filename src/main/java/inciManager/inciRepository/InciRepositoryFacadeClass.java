package inciManager.inciRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import inciManager.entities.Incidencia;
import inciManager.entities.Operator;

@Repository
public class InciRepositoryFacadeClass implements InciRepositoryFacade {

	@Autowired
	private IncidenceRepository inciRepo;

	@Autowired
	private OperatorRepository operatorRepo;

	@Override
	public Incidencia saveIncidence(Incidencia incidencia) {
		return inciRepo.save(incidencia);
	}

	@Override
	public List<Incidencia> getIncidence(String identificador) {
		return inciRepo.findByIdentificadorAgente(identificador);
	}

	@Override
	public List<Operator> getOperators() {
		List<Operator> aux = new ArrayList<Operator>();
		for (Operator operator : operatorRepo.findAll())
			aux.add(operator);

		return aux;
	}

}
