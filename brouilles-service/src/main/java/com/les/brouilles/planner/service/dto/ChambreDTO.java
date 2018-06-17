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
public class ChambreDTO extends CommonEvenementDTO {

	public ChambreDTO() {

	}

	private boolean avecAcompte;

	private BigDecimal montant = BigDecimal.ZERO;

}