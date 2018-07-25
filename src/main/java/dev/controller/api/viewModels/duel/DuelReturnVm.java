package dev.controller.api.viewModels.duel;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.entites.Duel;

public class DuelReturnVm {

	@JsonProperty("duel_id")
	private Long id;

	public DuelReturnVm(Duel duel) {
		this.id = duel.getId();
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
