package com.les.brouilles.planner.service.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PostITDTO {

	public PostITDTO() {

	}

	private Long id;

	@NotNull
	private String nom;

	@NotNull
	@JsonSerialize(as = Date.class)
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "Europe/Paris")
	private Date debut;

	private String heureDebut = "00:00";

	// Pour compatibilité avec les événements
	private String typeEvenement = "POST-IT";

}