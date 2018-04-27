package nc.noumea.mairie.bilan.energie.business.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nc.noumea.mairie.bilan.energie.contract.enumeration.TypeAnomalie;

/**
 * Entity Fichier Anomalie
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Table(name="FICHIER_ANOMALIE")
public class FichierAnomalieEntity extends AbstractEntity {
	

	/** Identifiant technique */
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "idFichierAnomalieGenerator")
	@SequenceGenerator( name = "idFichierAnomalieGenerator", sequenceName = "SEQ_FICHIER_ANOMALIE_ID", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	/** Fichier auquel est rattaché l'anomalie */
	@ManyToOne
	@JoinColumn(name = "FICHIER_ID")
	private FichierFactureEntity fichierFacture;
	
	/** Type */
	@Column(name = "TYPE")
	@Enumerated(EnumType.STRING)
	private TypeAnomalie type;
	
	/** Données de l'anomalie */
	@Column(name = "DONNEES")
	private String donnees;
	
	/** Anomalie traitée ? */
	@Column(name ="TRAITEE")
	private boolean traitee;

	/**
	 * Constructeur FichierAnomalieEntity
	 * 
	 */
	public FichierAnomalieEntity() {

	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the fichierFacture
	 */
	public FichierFactureEntity getFichierFacture() {
		return fichierFacture;
	}

	/**
	 * @param fichierFacture the fichierFacture to set
	 */
	public void setFichierFacture(FichierFactureEntity fichierFacture) {
		this.fichierFacture = fichierFacture;
	}

	/**
	 * @return the type
	 */
	public TypeAnomalie getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TypeAnomalie type) {
		this.type = type;
	}

	/**
	 * @return the donnees
	 */
	public String getDonnees() {
		return donnees;
	}

	/**
	 * @param donnees the donnees to set
	 */
	public void setDonnees(String donnees) {
		this.donnees = donnees;
	}

	/**
	 * @return the traitee
	 */
	public boolean isTraitee() {
		return traitee;
	}

	/**
	 * @param traitee the traitee to set
	 */
	public void setTraitee(boolean traitee) {
		this.traitee = traitee;
	}

	
}
