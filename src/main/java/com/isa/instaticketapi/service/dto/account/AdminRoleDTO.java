package com.isa.instaticketapi.service.dto.account;

import javax.validation.constraints.Size;

public class AdminRoleDTO {

	private Long id;

	public AdminRoleDTO() {
		// TODO Auto-generated constructor stub
	}

	public AdminRoleDTO(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
