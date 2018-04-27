package nc.noumea.mairie.bilan.energie.contract.dto;

import java.util.Date;

import nc.noumea.mairie.bilan.energie.contract.dto.annotations.IdDto;

/**
 * DTO Parametrage
 * 
 * @author Greg Dujardin
 * 
 */
public class Parametrage implements DtoModel {

	private static final long serialVersionUID = 1L;

	/** Identifiant technique */
	@IdDto
	private Long id;

	/** Version de l'entité */
	private long version;
	
	/** Parametre */
	private String parametre;

	/** Valeur */
	private String valeur;
	
	/** Description */
	private String description;

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
	 * @param id
	 *            the id to set
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
	 * @return the parametre
	 */
	public String getParametre() {
		return parametre;
	}

	/**
	 * @param parametre
	 *            the parametre to set
	 */
	public void setParametre(String parametre) {
		this.parametre = parametre;
	}

	/**
	 * @return the valeur
	 */
	public String getValeur() {
		return valeur;
	}

	/**
	 * @param valeur
	 *            the valeur to set
	 */
	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the dateCreation
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * @param dateCreation
	 *            the dateCreation to set
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
	 * @param auteurCreation
	 *            the auteurCreation to set
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
	 * @param dateModif
	 *            the dateModif to set
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
	 * @param auteurModif
	 *            the auteurModif to set
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
