package com.les.brouilles.planner.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MariageDTO extends CommonEvenementDTO {

	public MariageDTO() {

	}

	private boolean locationSalle;

	private boolean avecRepas;

	private boolean avecCocktail;

	private int nbPersonnesRepas = 0;

	private int nbPersonnesCocktail = 0;

	private boolean avecBrunch;

}