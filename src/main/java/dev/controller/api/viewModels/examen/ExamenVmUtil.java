package dev.controller.api.viewModels.examen;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.entites.Classe;
import dev.entites.Examen;
import dev.entites.Quizz;
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
	
	
	@Transactional
	public Examen ExamenVmCreateToEntity(ExamenVmCreate examenVm){
		
		Examen examen = new Examen();
		
		Classe classe = new Classe();	
		classe.setId(examenVm.getClasseId());
		Quizz quizz = new Quizz();
		quizz.setId(examenVm.getQuizzId());
		
		examen.setClasse(classe);
		examen.setQuizz(quizz);
		examen.setTitre(examenVm.getTitre());
		
		return examen;
	}
	
	@Transactional
	public Examen ExamenVmCreateToEntity(ExamenVmCreate examenVm, Long id){
		
		Examen examen = ExamenVmCreateToEntity(examenVm);
		examen.setId(id);
		
		return examen;
	}
}
