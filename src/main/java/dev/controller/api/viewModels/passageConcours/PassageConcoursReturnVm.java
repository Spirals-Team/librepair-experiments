package dev.controller.api.viewModels.passageConcours;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PassageConcoursReturnVm {
	
	@JsonProperty("passage_concours_id")
	private Long id;

	public PassageConcoursReturnVm(Long id){
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
