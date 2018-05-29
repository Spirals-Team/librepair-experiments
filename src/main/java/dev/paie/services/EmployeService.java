package dev.paie.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.paie.entite.RemunerationEmploye;
import dev.paie.repository.EmployeRepository;

@Service
public class EmployeService {
	@Autowired
	private EmployeRepository employeRepository;

	public List<RemunerationEmploye> getLesEmploye() {
		return employeRepository.findAll();
	}

	public void saveEmp(RemunerationEmploye emp) {
		employeRepository.save(emp);
	}
}
