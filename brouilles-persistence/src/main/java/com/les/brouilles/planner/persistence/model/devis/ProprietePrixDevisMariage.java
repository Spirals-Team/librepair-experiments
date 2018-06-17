package com.les.brouilles.planner.persistence.model.devis;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import com.les.brouilles.planner.persistence.model.devis.enums.TypeProprietePrix;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PROP_PRIX_DEVIS_MARIAGE")
public class ProprietePrixDevisMariage {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROP_MARIAGE")
	@SequenceGenerator(sequenceName = "SEQ_PROP_MARIAGE", allocationSize = 1, name = "SEQ_PROP_MARIAGE")
	private Long id;

	@NotNull
	@Column(name = "ORDER_GROUP", nullable = false)
	private String orderGroup;

	@NotNull
	@Column(name = "NOM", unique = true, nullable = false)
	private String nom;

	@NotNull
	@Column(name = "LABEL", nullable = false)
	private String label;

	@NotNull
	@Column(name = "TYPE_PROPRIETE", nullable = false)
	@Enumerated(EnumType.STRING)
	private TypeProprietePrix typeProprietePrix;

	@Column(name = "REMISE", nullable = false)
	private boolean remise = false;

	@Column(name = "QUANTITE", nullable = false)
	private int quantite = 0;

	@Column(name = "PRIX_UNIT_TTC", nullable = false)
	private BigDecimal prixUnitaireTTC = BigDecimal.ZERO;

	@PrePersist
	@PreUpdate
	public void check() {
		if (StringUtils.contains(nom, " ")) {
			throw new IllegalArgumentException("Le champ nom ne peux pas contenir d'espace");
		}
	}

}
