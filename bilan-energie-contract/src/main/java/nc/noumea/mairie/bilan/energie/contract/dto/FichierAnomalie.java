package nc.noumea.mairie.bilan.energie.contract.dto;

import java.util.Date;

import nc.noumea.mairie.bilan.energie.contract.dto.annotations.IdDto;
import nc.noumea.mairie.bilan.energie.contract.enumeration.TypeAnomalie;

/**
 * DTO Fichier Anomalie
 * 
 * @author Greg Dujardin
 *
 */
public class FichierAnomalie implements DtoModel {

	private static final long serialVersionUID = 1L;


	/** Identifiant technique */
	@IdDto
	private Long id;
	
	/** Version */
	private long version;
	
	/** Identifiant du fichier de facture */
	private Long idFichierFacture;

	/** Type */
	private TypeAnomalie type;
	
	/** Données de l'anomalie */
	private String donnees;
	
	/** Date de modification */
	private Date dateCreation;
	
	/** Auteur de la dernière modification */
	private String auteurCreation;

	/** Date de modification */
	private Date dateModif;
	
	/** Auteur de la dernière modification */
	private String auteurModif;
	
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
	 * @return the version
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(long version) {
		this.version = version;
	}

	/**
	 * @return the idFichierFacture
	 */
	public Long getIdFichierFacture() {
		return idFichierFacture;
	}

	/**
	 * @param idFichierFacture the idFichierFacture to set
	 */
	public void setIdFichierFacture(Long idFichierFacture) {
		this.idFichierFacture = idFichierFacture;
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
	 * @return the dateCreation
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * @param dateCreation the dateCreation to set
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	/**
	 * @return the auteurCreation
	 */
	public String getAuteurCreation() {
		return auteurCreation;
	}

	/**
	 * @param auteurCreation the auteurCreation to set
	 */
	public void setAuteurCreation(String auteurCreation) {
		this.auteurCreation = auteurCreation;
	}

	/**
	 * @return the dateModif
	 */
	public Date getDateModif() {
		return dateModif;
	}

	/**
	 * @param dateModif the dateModif to set
	 */
	public void setDateModif(Date dateModif) {
		this.dateModif = dateModif;
	}

	/**
	 * @return the auteurModif
	 */
	public String getAuteurModif() {
		return auteurModif;
	}

	/**
	 * @param auteurModif the auteurModif to set
	 */
	public void setAuteurModif(String auteurModif) {
		this.auteurModif = auteurModif;
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

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/** Anomalie traitée ? */
	private boolean traitee;

}
