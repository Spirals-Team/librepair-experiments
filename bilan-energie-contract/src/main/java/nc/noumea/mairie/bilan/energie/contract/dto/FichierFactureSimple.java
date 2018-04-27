package nc.noumea.mairie.bilan.energie.contract.dto;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.annotations.IdDto;
import nc.noumea.mairie.bilan.energie.contract.enumeration.EtatFichier;

/**
 * DTO Fichier Facture
 * 
 * @author Greg Dujardin
 *
 */
public class FichierFactureSimple implements DtoModel {

	private static final long serialVersionUID = 1L;

	/** Identifiant technique */
	@IdDto
	private Long id;

	/** Version */
	private long version;
	
	/** Liste des anomalies du fichier */
	private List<FichierAnomalie> listeAnomalie;
	
	/** Nom du fichier */
	private String nom;
	
	/** MD5 du fichier */
	private String md5;
	
	/** Taille du fichier */
	private Integer taille;
	
	/** Date d'import */
	private Date dateImport;

	/** Date d'intégration */
	private Date dateIntegration;
	
	/** Nombre de lignes */
	private Integer nbLignes;
	
	/** Nombre de lignes en erreurs */
	private Integer nbErreurs;
	
	/** Nombre d'erreurs traitées */
	private Integer nbErreursTraitees;
	
	/** Etat */
	private EtatFichier etat;
	
	/** Detail de l'erreur */
	private String detailErreur;
	
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
	 * @return the listeAnomalie
	 */
	public List<FichierAnomalie> getListeAnomalie() {
		return listeAnomalie;
	}

	/**
	 * @param listeAnomalie the listeAnomalie to set
	 */
	public void setListeAnomalie(List<FichierAnomalie> listeAnomalie) {
		this.listeAnomalie = listeAnomalie;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * get Md5
	 *
	 * @return md5
	 */
	public String getMd5() {
		return md5;
	}

	/**
	 * set Md5
	 *
	 * @param md5 Md5 to set
	 */
	public void setMd5(String md5) {
		this.md5 = md5;
	}

	/**
	 * get Taille
	 *
	 * @return taille
	 */
	public Integer getTaille() {
		return taille;
	}

	/**
	 * set Taille
	 *
	 * @param taille Taille to set
	 */
	public void setTaille(Integer taille) {
		this.taille = taille;
	}

	/**
	 * @return the dateImport
	 */
	public Date getDateImport() {
		return dateImport;
	}

	/**
	 * @param dateImport the dateImport to set
	 */
	public void setDateImport(Date dateImport) {
		this.dateImport = dateImport;
	}

	/**
	 * @return the dateIntegration
	 */
	public Date getDateIntegration() {
		return dateIntegration;
	}

	/**
	 * @param dateIntegration the dateIntegration to set
	 */
	public void setDateIntegration(Date dateIntegration) {
		this.dateIntegration = dateIntegration;
	}

	/**
	 * @return the nbLignes
	 */
	public Integer getNbLignes() {
		return nbLignes;
	}

	/**
	 * @param nbLignes the nbLignes to set
	 */
	public void setNbLignes(Integer nbLignes) {
		this.nbLignes = nbLignes;
	}

	/**
	 * @return the nbErreurs
	 */
	public Integer getNbErreurs() {
		return nbErreurs;
	}

	/**
	 * @param nbErreurs the nbErreurs to set
	 */
	public void setNbErreurs(Integer nbErreurs) {
		this.nbErreurs = nbErreurs;
	}

	/**
	 * @return the nbErreursTraitees
	 */
	public Integer getNbErreursTraitees() {
		return nbErreursTraitees;
	}

	/**
	 * @param nbErreursTraitees the nbErreursTraitees to set
	 */
	public void setNbErreursTraitees(Integer nbErreursTraitees) {
		this.nbErreursTraitees = nbErreursTraitees;
	}

	/**
	 * @return the etat
	 */
	public EtatFichier getEtat() {
		return etat;
	}

	/**
	 * @param etat the etat to set
	 */
	public void setEtat(EtatFichier etat) {
		this.etat = etat;
	}

	/**
	 * @return the detailErreur
	 */
	public String getDetailErreur() {
		return detailErreur;
	}

	/**
	 * @param detailErreur the detailErreur to set
	 */
	public void setDetailErreur(String detailErreur) {
		this.detailErreur = detailErreur;
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
