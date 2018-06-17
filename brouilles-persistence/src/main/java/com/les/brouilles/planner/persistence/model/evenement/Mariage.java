package com.les.brouilles.planner.persistence.model.evenement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "EVT_MARIAGE")
@PrimaryKeyJoinColumn(name = "common_evenement")
public class Mariage extends CommonEvenement {

	@Column(name = "LOCATION_SALLE")
	private boolean locationSalle;

	@Column(name = "AVEC_REPAS")
	private boolean avecRepas;

	@Column(name = "AVEC_COCKTAIL")
	private boolean avecCocktail;

	@NotNull
	@Column(name = "NB_PERSONNES_REPAS", nullable = false)
	private int nbPersonnesRepas = 0;

	@NotNull
	@Column(name = "NB_PERSONNES_COCKTAIL", nullable = false)
	private int nbPersonnesCocktail = 0;

	@Column(name = "AVEC_BRUNCH")
	private boolean avecBrunch;

	@PrePersist
	public void before() {
		super.setNbPersonnes(nbPersonnesRepas);
	}
}
