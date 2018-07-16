package dev.controller.api.viewModels;

public class ExamenVm extends BaseVm{

	private String nom;

	public ExamenVm(Long id,String nom, ClasseVm classe,QuizzVm quizz){
		super(id);
	}

}
