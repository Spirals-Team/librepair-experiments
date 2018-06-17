package com.les.brouilles.planner.persistence.model.devis;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.les.brouilles.planner.persistence.model.evenement.CommonEvenement;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@ToString(exclude = "json")
@Table(name = "DEVIS")
public class Devis {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DEVIS")
	@SequenceGenerator(sequenceName = "SEQ_DEVIS", allocationSize = 1, name = "SEQ_DEVIS")
	private Long id;

	@NotNull
	@Column(name = "NUMERO", unique = true, nullable = false)
	private String numeroDevis;

	@NotNull
	@Lob
	@Column(name = "JSON", nullable = false)
	private String json;

	@Column(name = "ACOMPTE_VERSE", nullable = false)
	private BigDecimal acompte = BigDecimal.ZERO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EVENEMENT_ID")
	private CommonEvenement evenement;

	@NotNull
	@Column(name = "ETAT", nullable = false)
	@Enumerated(EnumType.STRING)
	private Etat etat;

	@Column(name = "TYPE_DEVIS", nullable = false)
	@Enumerated(EnumType.STRING)
	private TypeDevis typeDevis;

	@Column(name = "COMMENTAIRE", length = 512)
	private String commentaire;

	public enum Etat {

		CREE,

		ENVOYE,

		SIGNE,

		REFUSE;
	}

	public enum TypeDevis {

		MARIAGE,

		LOCATION_SALLE,

		REPAS,

		LIBERTE
	}

}
