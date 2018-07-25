package dev.controller.api.stagiaire;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StagiaireCreeVm {

	@JsonProperty("stagiaire_id")
	private Long id;

	public StagiaireCreeVm() {
		super();
	}

	public StagiaireCreeVm(Long id) {
		super();
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
