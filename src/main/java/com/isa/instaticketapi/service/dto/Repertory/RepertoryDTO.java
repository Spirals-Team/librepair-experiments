package com.isa.instaticketapi.service.dto.Repertory;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RepertoryDTO {

	private Long id;

	@NotNull
	@Size(min = 10, max = 10)
	private String date;

	public RepertoryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RepertoryDTO(Long id, String date) {
		super();
		this.id = id;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
