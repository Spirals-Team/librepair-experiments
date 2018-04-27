package nc.noumea.mairie.bilan.energie.contract.dto;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.annotations.IdDto;

/**
 * DTO Compteur
 * 
 * @author Greg Dujardin
 * 
 */
public class Compteur implements DtoModel, Periodique {

	private static final long serialVersionUID = 1L;

	/** Identifiant technique */
	@IdDto
	private Long id;

	/** Version de l'entité */
	private long version;

	/** Liste des comptages */
	private List<Comptage> listeComptage;

	/** Numéro du compteur */
	private String numCompteur;

	/** Police à laquelle est rattachée le compteur */
	private Long idPolice;

	/** Adresse */
	private AdresseLabel adresse;

	/** Identifiant du compteur père */
	private Long idCompteurPere;

	/** Type de compteur */
	private CodeLabel type;

	/** Type d'emplacement */
	private CodeLabel typeEmplacement;

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
	 * @return the listeComptage
	 */
	public List<Comptage> getListeComptage() {
		return listeComptage;
	}

	/**
	 * @param listeComptage
	 *            the listeComptage to set
	 */
	public void setListeComptage(List<Comptage> listeComptage) {
		this.listeComptage = listeComptage;
	}

	/**
	 * @return the numCompteur
	 */
	public String getNumCompteur() {
		return numCompteur;
	}

	/**
	 * @param numCompteur
	 *            the numCompteur to set
	 */
	public void setNumCompteur(String numCompteur) {
		this.numCompteur = numCompteur;
	}

	/**
	 * @return the idPolice
	 */
	public Long getIdPolice() {
		return idPolice;
	}

	/**
	 * @param idPolice
	 *            the idPolice to set
	 */
	public void setIdPolice(Long idPolice) {
		this.idPolice = idPolice;
	}

	/**
	 * @return the adresse
	 */
	public AdresseLabel getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(AdresseLabel adresse) {
		this.adresse = adresse;
	}

	/**
	 * @return the idCompteurPere
	 */
	public Long getIdCompteurPere() {
		return idCompteurPere;
	}

	/**
	 * @param idCompteurPere
	 *            the idCompteurPere to set
	 */
	public void setIdCompteurPere(Long idCompteurPere) {
		this.idCompteurPere = idCompteurPere;
	}

	/**
	 * @return the type
	 */
	public CodeLabel getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(CodeLabel type) {
		this.type = type;
	}

	/**
	 * @return the typeEmplacement
	 */
	public CodeLabel getTypeEmplacement() {
		return typeEmplacement;
	}

	/**
	 * @param typeEmplacement
	 *            the typeEmplacement to set
	 */
	public void setTypeEmplacement(CodeLabel typeEmplacement) {
		this.typeEmplacement = typeEmplacement;
	}

	/**
	 * @return the dateDebut
	 */
	public Date getDateDebut() {
		return dateDebut;
	}

	/**
	 * @param dateDebut
	 *            the dateDebut to set
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
	 * @param dateFin
	 *            the dateFin to set
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
