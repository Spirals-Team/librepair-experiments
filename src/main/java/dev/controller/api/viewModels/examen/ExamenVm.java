package dev.controller.api.viewModels.examen;

import dev.controller.api.viewModels.BaseVm;
import dev.entites.Examen;

public class ExamenVm extends BaseVm{

	private String titre;
	private ClasseVm classe;
	private QuizzVm quizz;

	public ExamenVm(Examen examen){
		super(examen.getId());
		this.titre = examen.getTitre();
		classe = new ClasseVm(examen.getClasse().getId(),examen.getClasse().getNom());
		quizz = new QuizzVm(examen.getQuizz().getId(), examen.getQuizz().getTitre(), examen.getQuizz().getQuestions().size());
		
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public ClasseVm getClasse() {
		return classe;
	}

	public void setClasse(ClasseVm classe) {
		this.classe = classe;
	}

	public QuizzVm getQuizz() {
		return quizz;
	}

	public void setQuizz(QuizzVm quizz) {
		this.quizz = quizz;
	}
	
	

}
