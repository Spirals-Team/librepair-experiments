package com.les.brouilles.planner.service.dto;

import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ClientDTO {

	public ClientDTO() {

	}

	private Long id;

	private String numeroClient;

	@NotNull
	private String nom;

	@NotNull
	private String prenom;

	@NotNull
	private String categorie;

	private String denomination;

	private String adresse;

	private String tel1;

	private String tel2;

	private String mail1;

	private String mail2;

}