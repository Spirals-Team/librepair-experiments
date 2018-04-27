package nc.noumea.mairie.bilan.energie.contract.dto;

import java.util.Date;

import nc.noumea.mairie.bilan.energie.contract.dto.annotations.IdDto;

/**
 * DTO Comptage
 * 
 * @author Greg Dujardin
 * 
 */
public class Comptage implements DtoModel {

	private static final long serialVersionUID = 1L;

	/** Version de l'entité */
	private long version;

	/** Identifiant technique */
	@IdDto
	private Long id;

	/** Compteur auquel est rattaché le comptage */
	private Long idCompteur;

	/** Numéro de facturation */
	private Long numFacture;

	/** Année de facturation */
	private Long annee;

	/** Mois de facturation */
	private Long mois;

	/** Type de facturation */
	private String type;

	/** Valeur de l'index */
	private Long valeur;

	/** Date du relevé */
	private Date date;

	/** Montant net */
	private Long montantNet;

	/** Consommation relevé */
	private Long consommation;

	/** Bordereau */
	private String bordereau;

	/** Type de facturation précédent */
	private String typePrecedent;

	/** Date du relevé précédent */
	private Date datePrecedent;

	/** Valeur de de l'index précédent */
	private Long valeurPrecedent;

	/** Date de modification */
	private Date dateCreation;

	/** Auteur de la dernière modification */
	private String auteurCreation;

	/** Date de modification */
	private Date dateModif;

	/** Auteur de la dernière modification */
	private String auteurModif;
	
	/** Consommation des compteurs divisionnaires */
	private Long consommationCptDivisionnaire;

	/**
	 * @return the version
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
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
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the idCompteur
	 */
	public Long getIdCompteur() {
		return idCompteur;
	}

	/**
	 * @param idCompteur
	 *            the idCompteur to set
	 */
	public void setIdCompteur(Long idCompteur) {
		this.idCompteur = idCompteur;
	}

	/**
	 * get NumFacture
	 * 
	 * @return numFacture
	 */
	public Long getNumFacture() {
		return numFacture;
	}

	/**
	 * set NumFacture
	 * 
	 * @param numFacture NumFacture to set
	 */
	public void setNumFacture(Long numFacture) {
		this.numFacture = numFacture;
	}

	/**
	 * @return the annee
	 */
	public Long getAnnee() {
		return annee;
	}

	/**
	 * @param annee
	 *            the annee to set
	 */
	public void setAnnee(Long annee) {
		this.annee = annee;
	}

	/**
	 * @return the consommation
	 */
	public Long getConsommation() {
		return consommation;
	}

	/**
	 * @param consommation
	 *            the consommation to set
	 */
	public void setConsommation(Long consommation) {
		this.consommation = consommation;
	}

	/**
	 * @return the mois
	 */
	public Long getMois() {
		return mois;
	}

	/**
	 * @param mois
	 *            the mois to set
	 */
	public void setMois(Long mois) {
		this.mois = mois;
	}

	/**
	 * @return the montantNet
	 */
	public Long getMontantNet() {
		return montantNet;
	}

	/**
	 * @param montantNet
	 *            the montantNet to set
	 */
	public void setMontantNet(Long montantNet) {
		this.montantNet = montantNet;
	}

	/**
	 * @return the valeur
	 */
	public Long getValeur() {
		return valeur;
	}

	/**
	 * Retourne la valeur à afficher d'un comptage
	 * 
	 * @return Valeur à afficher
	 */
	public Long getValeurAffichage() {
		if (getValeur() == null || getValeur() == 0)
			return null;
		else
			return getValeur();
	}

	/**
	 * @param valeur
	 *            the valeur to set
	 */
	public void setValeur(Long valeur) {
		this.valeur = valeur;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
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

	/**
	 * Retourne la période (concaténation de Année et Mois)
	 * 
	 * @return Période
	 */
	public String getPeriode() {
		String periode = getAnnee() + "/";

		if (getMois() < 10)
			periode = periode + "0";

		periode = periode + getMois();

		return periode;
	}

	/**
	 * @return the bordereau
	 */
	public String getBordereau() {
		return bordereau;
	}

	/**
	 * @param bordereau
	 *            the bordereau to set
	 */
	public void setBordereau(String bordereau) {
		this.bordereau = bordereau;
	}

	/**
	 * @return the typePrecedent
	 */
	public String getTypePrecedent() {
		return typePrecedent;
	}

	/**
	 * @param typePrecedent
	 *            the typePrecedent to set
	 */
	public void setTypePrecedent(String typePrecedent) {
		this.typePrecedent = typePrecedent;
	}

	/**
	 * @return the datePrecedent
	 */
	public Date getDatePrecedent() {
		return datePrecedent;
	}

	/**
	 * @param datePrecedent
	 *            the datePrecedent to set
	 */
	public void setDatePrecedent(Date datePrecedent) {
		this.datePrecedent = datePrecedent;
	}

	/**
	 * @return the valeurPrecedent
	 */
	public Long getValeurPrecedent() {
		return valeurPrecedent;
	}

	/**
	 * @param valeurPrecedent
	 *            the valeurPrecedent to set
	 */
	public void setValeurPrecedent(Long valeurPrecedent) {
		this.valeurPrecedent = valeurPrecedent;
	}

	/**
	 * get ConsommationCptDivisionnaire
	 * 
	 * @return consommationCptDivisionnaire
	 */
	public Long getConsommationCptDivisionnaire() {
		return consommationCptDivisionnaire;
	}

	/**
	 * set ConsommationCptDivisionnaire
	 * 
	 * @param consommationCptDivisionnaire ConsommationCptDivisionnaire to set
	 */
	public void setConsommationCptDivisionnaire(
			Long consommationCptDivisionnaire) {
		this.consommationCptDivisionnaire = consommationCptDivisionnaire;
	}

}
