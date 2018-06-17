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

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CLIENT")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CLIENT")
	@SequenceGenerator(sequenceName = "SEQ_CLIENT", allocationSize = 1, name = "SEQ_CLIENT")
	private Long id;

	@NotNull
	@Column(name = "NUMERO", unique = true)
	private String numeroClient;

	@NotNull
	@Column(name = "NOM", nullable = false)
	private String nom;

	@NotNull
	@Column(name = "PRENOM", nullable = false)
	private String prenom;

	@NotNull
	@Column(name = "CATEGORIE", nullable = false)
	@Enumerated(EnumType.STRING)
	private Categorie categorie;

	@Column(name = "DENOMINATION")
	private String denomination;

	@Column(name = "ADRESSE")
	private String adresse;

	@Column(name = "TELEPHONE_1")
	private String tel1;

	@Column(name = "TELEPHONE_2")
	private String tel2;

	@Column(name = "MAIL_1")
	private String mail1;

	@Column(name = "MAIL_2")
	private String mail2;

	public enum Categorie {
		PARTICULIER, ASSOCIATION, ENTREPRISE, AUTRES;
	}

}
