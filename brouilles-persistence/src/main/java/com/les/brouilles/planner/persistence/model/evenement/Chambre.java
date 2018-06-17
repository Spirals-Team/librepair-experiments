package com.les.brouilles.planner.persistence.model.evenement;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "EVT_CHAMBRE")
@PrimaryKeyJoinColumn(name = "common_evenement")
public class Chambre extends CommonEvenement {

	@Column(name = "AVEC_ACOMPTE")
	private boolean avecAcompte;

	@Column(name = "MONTANT", nullable = false)
	private BigDecimal montant = BigDecimal.ZERO;

}
