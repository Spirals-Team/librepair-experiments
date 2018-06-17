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
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "FACTURE")
public class Facture {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FACTURE")
	@SequenceGenerator(sequenceName = "SEQ_FACTURE", allocationSize = 1, name = "SEQ_FACTURE")
	private Long id;

	@Column(name = "NUMERO", unique = true)
	private String numeroFacture;

	// FIXME : mieux de stocker en base que de renvoyer une url de disque?
	@NotNull
	@Column(name = "URL", nullable = false)
	private String url;

	@NotNull
	@Column(name = "TYPE_FACTURE", nullable = false)
	@Enumerated(EnumType.STRING)
	private Type typeFacture;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUT", nullable = false)
	private Status statut;

	public enum Type {

		MON_TYPE;
	}

	public enum Status {

		REGLE;

	}

}
