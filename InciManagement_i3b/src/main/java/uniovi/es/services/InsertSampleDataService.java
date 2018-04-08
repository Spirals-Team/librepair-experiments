package uniovi.es.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniovi.es.entities.Operator;
import uniovi.es.repositories.OperatorRepository;

@Service
public class InsertSampleDataService {

	@Autowired
	private OperatorRepository operatorRepository;

	@PostConstruct
	public void init() {
		if (operatorRepository.count() == 0)
			addMockOperators();
	}

	private void addMockOperators() {
		Operator o1 = new Operator("operator1", "asd");
		Operator o2 = new Operator("operator2", "asd");
		Operator o3 = new Operator("operator3", "asd");
		Operator o4 = new Operator("operator4", "asd");
		operatorRepository.insert(o1);
		operatorRepository.insert(o2);
		operatorRepository.insert(o3);
		operatorRepository.insert(o4);
	}
}
