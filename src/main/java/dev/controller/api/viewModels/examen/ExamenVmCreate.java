package dev.controller.api.viewModels.examen;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExamenVmCreate{

	@JsonProperty("classe_id")
	private Long classeId;
	
	@JsonProperty("quizz_id")
	private Long quizzId;
	
	private String titre;
	
	public ExamenVmCreate(){
		
	}

	public Long getClasseId() {
		return classeId;
	}

	public void setClasseId(Long classeId) {
		this.classeId = classeId;
	}

	public Long getQuizzId() {
		return quizzId;
	}

	public void setQuizzId(Long quizzId) {
		this.quizzId = quizzId;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	
}
