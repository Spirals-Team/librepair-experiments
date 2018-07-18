package dev.controller.api.viewModels.examen;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.entites.Examen;
import dev.metiers.ExamenService;

@Service
public class ExamenVmUtil {

	private ExamenService examenService;
	
	public ExamenVmUtil(ExamenService examenService){
		this.examenService = examenService;
	}
	
	@Transactional
	public List<ExamenVm> listAllExam(){
		
        List<ExamenVm> listExam  = examenService.lister().stream().map(ExamenVm::new).collect(Collectors.toList());

        return listExam;
	}
	
	@Transactional
	public ExamenVm createExamen(Examen examen){
		
		return new ExamenVm(examen);

	}
}
