package com.les.brouilles.planner.service.dto;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LiberteDTO extends CommonEvenementDTO {

	public LiberteDTO() {

	}

	private String menu1;

	private BigDecimal prix1 = BigDecimal.ZERO;

	private String menu2;

	private BigDecimal prix2 = BigDecimal.ZERO;

	private String repasSelection;

	private BigDecimal prixAlcool = BigDecimal.ZERO;

	private String contenuAlcool;

}