package com.les.brouilles.planner.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "COMPTEUR", uniqueConstraints = { @UniqueConstraint(columnNames = { "ANNEE", "TYPE_COMPTEUR" }) })
public class Compteur {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMPTEUR")
	@SequenceGenerator(sequenceName = "SEQ_COMPTEUR", allocationSize = 1, name = "SEQ_COMPTEUR")
	private Long id;

	@NotNull
	@Column(name = "ANNEE", nullable = false)
	private String annee;

	@NotNull
	@Column(name = "VALEUR", nullable = false)
	private long valeur;

	@NotNull
	@Column(name = "TYPE_COMPTEUR", nullable = false)
	@Enumerated(EnumType.STRING)
	private TypeCompteur typeCompteur;

	public enum TypeCompteur {
		CLIENT, EVENEMENT, DEVIS, FACTURE;
	}

}
