package nc.noumea.mairie.bilan.energie.contract.dto;

import java.util.Date;

import nc.noumea.mairie.bilan.energie.contract.dto.annotations.IdDto;

/**
 * DTO Type Police
 * 
 * @author Greg Dujardin
 *
 */
public class TypePolice implements DtoModel {

	private static final long serialVersionUID = 1L;

	/** Identifiant technique */
	@IdDto
	private Long id;
	
	/** Version de l'entité */
	private long version;
	
	/** Libelle */
	private String libelle;
	
	/** Description */
	private String description;
	
	/** Comptage modifiable ? */
	private boolean comptageModifiable;

	/** Catégorie du type de police */
	private CodeLabel categorie;
	
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
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * get Description
	 *
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * set Description
	 *
	 * @param description Description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the comptageModifiable
	 */
	public boolean isComptageModifiable() {
		return comptageModifiable;
	}

	/**
	 * @param comptageModifiable the comptageModifiable to set
	 */
	public void setComptageModifiable(boolean comptageModifiable) {
		this.comptageModifiable = comptageModifiable;
	}

	/**
	 * @return the categorie
	 */
	public CodeLabel getCategorie() {
		return categorie;
	}

	/**
	 * @param categorie the categorie to set
	 */
	public void setCategorie(CodeLabel categorie) {
		this.categorie = categorie;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
