package com.les.brouilles.planner.persistence.model.evenement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "EVT_LOCATION_SALLE")
@PrimaryKeyJoinColumn(name = "common_evenement")
public class LocationSalle extends CommonEvenement {

	@NotNull
	@Column(name = "TYPE_TABLES", nullable = false)
	@Enumerated(EnumType.STRING)
	private TypeTables typeTables;

	public enum TypeTables {
		RONDES, CARREES;
	}
}
