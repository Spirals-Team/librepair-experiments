package com.les.brouilles.planner.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.les.brouilles.planner.service.constraint.DevisJsonValide;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString(exclude = "json")
@DevisJsonValide
public class DevisDTO {

	private Long id;

	private String numeroDevis;

	@NotNull
	private String typeDevis;

	@NotNull
	private String json;

	private BigDecimal acompte = BigDecimal.ZERO;

	private Long evenementId;

	private String etat;

	private String commentaire;

}
