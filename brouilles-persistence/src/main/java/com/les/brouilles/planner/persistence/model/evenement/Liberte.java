package com.les.brouilles.planner.persistence.model.evenement;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.les.brouilles.planner.persistence.model.evenement.enums.RepasSelection;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "EVT_LIBERTE")
@PrimaryKeyJoinColumn(name = "common_evenement")
public class Liberte extends CommonEvenement {

	@Column(name = "MENU1", length = 50)
	private String menu1;

	@Column(name = "PRIX1", nullable = false)
	private BigDecimal prix1 = BigDecimal.ZERO;

	@Column(name = "MENU2", length = 50)
	private String menu2;

	@Column(name = "PRIX2", nullable = false)
	private BigDecimal prix2 = BigDecimal.ZERO;

	@NotNull
	@Column(name = "REPAS_SELECTION", nullable = false)
	@Enumerated(EnumType.STRING)
	private RepasSelection repasSelection;

	@Column(name = "PRIX_ALCOOL", nullable = false)
	private BigDecimal prixAlcool = BigDecimal.ZERO;

	@Column(name = "CONTENU_ALCOOL")
	private String contenuAlcool;
}
