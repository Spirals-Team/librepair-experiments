package nc.noumea.mairie.bilan.energie.contract.dto;

import java.util.Date;

import nc.noumea.mairie.bilan.energie.contract.dto.annotations.IdDto;

/**
 * DTO Luminaire
 * 
 * @author Greg Dujardin
 *
 */
public class Luminaire implements DtoModel {

	private static final long serialVersionUID = 1L;
	
	/** Version de l'entité */
	private long version;
	
	/** Identifiant technique */
	@IdDto
	private Long id;
	
	/** Identifiant du support */
	private Long idSupport;

	/** Puissance nominale du luminaire */
	private long puissance;
	
	/** Type de source */
	private CodeLabel typeSource;
	
	/** Modèle du luminaire */
	private String modele;
	
	/** Luminaire avec gradation */
	private boolean gradation;
	
	/** Hauteur du luminaire */
	private Long hauteur;
	
	/** Complements */
	private String complements;
	
	/** Propriétaire */
	private CodeLabel proprietaire;
	
	/** Date de début d'existence */
	private Date dateDebut;
	
	/** Date de fin d'existence */
	private Date dateFin;
	
	/** Date de modification */
	private Date dateCreation;
	
	/** Auteur de la dernière modification */
	private String auteurCreation;

	/** Date de modification */
	private Date dateModif;
	
	/** Auteur de la dernière modification */
	private String auteurModif;
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
	 * @return the idSupport
	 */
	public Long getIdSupport() {
		return idSupport;
	}

	/**
	 * @param idSupport the idSupport to set
	 */
	public void setIdSupport(Long idSupport) {
		this.idSupport = idSupport;
	}

	/**
	 * @return the puissance
	 */
	public long getPuissance() {
		return puissance;
	}

	/**
	 * @param puissance the puissance to set
	 */
	public void setPuissance(long puissance) {
		this.puissance = puissance;
	}

	/**
	 * @return the typeSource
	 */
	public CodeLabel getTypeSource() {
		return typeSource;
	}

	/**
	 * @param typeSource the typeSource to set
	 */
	public void setTypeSource(CodeLabel typeSource) {
		this.typeSource = typeSource;
	}

	/**
	 * @return the modele
	 */
	public String getModele() {
		return modele;
	}

	/**
	 * @param modele the modele to set
	 */
	public void setModele(String modele) {
		this.modele = modele;
	}

	/**
	 * @return the gradation
	 */
	public boolean isGradation() {
		return gradation;
	}

	/**
	 * @param gradation the gradation to set
	 */
	public void setGradation(boolean gradation) {
		this.gradation = gradation;
	}

	/**
	 * @return the hauteur
	 */
	public Long getHauteur() {
		return hauteur;
	}

	/**
	 * @param hauteur the hauteur to set
	 */
	public void setHauteur(Long hauteur) {
		this.hauteur = hauteur;
	}

	/**
	 * @return the complements
	 */
	public String getComplements() {
		return complements;
	}

	/**
	 * @param complements the complements to set
	 */
	public void setComplements(String complements) {
		this.complements = complements;
	}

	/**
	 * @return the proprietaire
	 */
	public CodeLabel getProprietaire() {
		return proprietaire;
	}

	/**
	 * @param proprietaire the proprietaire to set
	 */
	public void setProprietaire(CodeLabel proprietaire) {
		this.proprietaire = proprietaire;
	}

	/**
	 * @return the dateDebut
	 */
	public Date getDateDebut() {
		return dateDebut;
	}

	/**
	 * @param dateDebut the dateDebut to set
	 */
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * @return the dateFin
	 */
	public Date getDateFin() {
		return dateFin;
	}

	/**
	 * @param dateFin the dateFin to set
	 */
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
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
