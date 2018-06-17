package com.les.brouilles.planner.persistence.model.evenement;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.les.brouilles.planner.persistence.exception.BrouillesPersistenceInvalidDatesDebutFinException;
import com.les.brouilles.planner.persistence.model.Client;
import com.les.brouilles.planner.persistence.model.evenement.enums.EtatEvenement;
import com.les.brouilles.planner.persistence.model.evenement.enums.TypeEvenement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "COMMON_EVENEMENT")
@Inheritance(strategy = InheritanceType.JOINED)
public class CommonEvenement {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EVENEMENT")
	@SequenceGenerator(sequenceName = "SEQ_EVENEMENT", allocationSize = 1, name = "SEQ_EVENEMENT")
	private Long id;

	@NotNull
	@Column(name = "NOM", length = 100, nullable = false)
	private String nom;

	@NotNull
	@Column(name = "NUMERO", unique = true)
	private String numeroEvenement;

	@NotNull
	@Column(name = "NB_PERSONNES", nullable = false)
	private int nbPersonnes = 0;

	@NotNull
	@Column(name = "DEBUT", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date debut;

	@NotNull
	@Column(name = "FIN", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fin;

	@Column(name = "COMMENTAIRE", length = 512)
	private String commentaire;

	@NotNull
	@Column(name = "ETAT", nullable = false)
	@Enumerated(EnumType.STRING)
	private EtatEvenement etatEvenement;

	@NotNull
	@Column(name = "TYPE_EVENEMENT", nullable = false)
	@Enumerated(EnumType.STRING)
	private TypeEvenement typeEvenement;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLIENT_ID")
	private Client client;

	@PrePersist
	public void validation() {
		if (debut.compareTo(fin) > 0) {
			throw new BrouillesPersistenceInvalidDatesDebutFinException(debut, fin);
		}
	}
}