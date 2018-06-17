package com.les.brouilles.planner.service.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class CommonEvenementDTO {

	protected Long id;

	@NotNull
	protected String nom;

	protected int nbPersonnes = 0;

	protected String numeroEvenement;

	@NotNull
	protected Long idClient;

	protected String infosClient;

	@NotNull
	protected String typeEvenement;

	protected String etatEvenement;

	@JsonSerialize(as = Date.class)
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "Europe/Paris")
	protected Date debut;

	@JsonSerialize(as = Date.class)
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "Europe/Paris")
	protected Date fin;

	protected String heureDebut = "00:00";

	protected String heureFin = "00:00";

	protected String commentaire;

}