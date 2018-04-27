package nc.noumea.mairie.bilan.energie.contract.dto;

import java.math.BigDecimal;
import java.util.Date;

import nc.noumea.mairie.bilan.energie.contract.dto.annotations.IdDto;

/**
 * DTO Facture
 * 
 * @author Greg Dujardin
 *
 */
public class Facture implements DtoModel {

	private static final long serialVersionUID = 1L;

	/** Identifiant technique */
	@IdDto
	private Long id;

	/** Fichier auquel est rattaché la facture */
	private Long idFichierFacture;
	
	/** Code */
	private Long code;

	/** Rgpmt */
	private String rgpmt;
	
	/** Année de facturation */
	private Long anneeFacturation;
	
	/** Mois de facturation */
	private Long moisFacturation;

	/** Numéro de police */
	private String numPolice;
	
	/** Numéro de facture */
	private Long numFacture;
	
	/** Tarif */
	private String tarif;
	
	/** Puissance */
	private BigDecimal puissance;
	
	/** Quartier */
	private Long quartier;
	
	/** Rue */
	private Long rue;
	
	/** Numéro dans la voie */
	private Long numVoie;
	
	/** Cptnv */
	private String cptnv;
	
	/** Description */
	private String description;
	
	/** Numéro de compteur */
	private String numCompteur;
	
	/** Nb de fils */
	private Long nbFils;
	
	/** Type de relevé précédente */
	private String typePrecedent;
	
	/** Date de relevé précédente */
	private Date datePrecedente;
	
	/** Valeur de l'indice précédent */
	private Long valeurIndicePrecedent;
	
	/** Type de facturation */
	private String typeReleve;
	
	/** Date de facturation */
	private Date dateReleve;
	
	/** Valeur de l'indice */
	private Long valeurIndice;
	
	/** Consommation relevé */
	private Long consommationReleve;
	
	/** concpt */
	private Long conscpt;
	
	/** Montant net */
	private Long montantNet;

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
	 * @return the code
	 */
	public Long getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Long code) {
		this.code = code;
	}

	/**
	 * @return the rgpmt
	 */
	public String getRgpmt() {
		return rgpmt;
	}

	/**
	 * @param rgpmt the rgpmt to set
	 */
	public void setRgpmt(String rgpmt) {
		this.rgpmt = rgpmt;
	}

	/**
	 * @return the anneeFacturation
	 */
	public Long getAnneeFacturation() {
		return anneeFacturation;
	}

	/**
	 * @param anneeFacturation the anneeFacturation to set
	 */
	public void setAnneeFacturation(Long anneeFacturation) {
		this.anneeFacturation = anneeFacturation;
	}

	/**
	 * @return the moisFacturation
	 */
	public Long getMoisFacturation() {
		return moisFacturation;
	}

	/**
	 * @param moisFacturation the moisFacturation to set
	 */
	public void setMoisFacturation(Long moisFacturation) {
		this.moisFacturation = moisFacturation;
	}

	/**
	 * @return the numPolice
	 */
	public String getNumPolice() {
		return numPolice;
	}

	/**
	 * @param numPolice the numPolice to set
	 */
	public void setNumPolice(String numPolice) {
		this.numPolice = numPolice;
	}

	/**
	 * @return the numFacture
	 */
	public Long getNumFacture() {
		return numFacture;
	}

	/**
	 * @param numFacture the numFacture to set
	 */
	public void setNumFacture(Long numFacture) {
		this.numFacture = numFacture;
	}

	/**
	 * @return the tarif
	 */
	public String getTarif() {
		return tarif;
	}

	/**
	 * @param tarif the tarif to set
	 */
	public void setTarif(String tarif) {
		this.tarif = tarif;
	}

	/**
	 * @return the puissance
	 */
	public BigDecimal getPuissance() {
		return puissance;
	}

	/**
	 * @param puissance the puissance to set
	 */
	public void setPuissance(BigDecimal puissance) {
		this.puissance = puissance;
	}

	/**
	 * @return the quartier
	 */
	public Long getQuartier() {
		return quartier;
	}

	/**
	 * @param quartier the quartier to set
	 */
	public void setQuartier(Long quartier) {
		this.quartier = quartier;
	}

	/**
	 * @return the rue
	 */
	public Long getRue() {
		return rue;
	}

	/**
	 * @param rue the rue to set
	 */
	public void setRue(Long rue) {
		this.rue = rue;
	}

	/**
	 * @return the numVoie
	 */
	public Long getNumVoie() {
		return numVoie;
	}

	/**
	 * @param numVoie the numVoie to set
	 */
	public void setNumVoie(Long numVoie) {
		this.numVoie = numVoie;
	}

	/**
	 * @return the cptnv
	 */
	public String getCptnv() {
		return cptnv;
	}

	/**
	 * @param cptnv the cptnv to set
	 */
	public void setCptnv(String cptnv) {
		this.cptnv = cptnv;
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
	 * @return the numCompteur
	 */
	public String getNumCompteur() {
		return numCompteur;
	}

	/**
	 * @param numCompteur the numCompteur to set
	 */
	public void setNumCompteur(String numCompteur) {
		this.numCompteur = numCompteur;
	}

	/**
	 * @return the nbFils
	 */
	public Long getNbFils() {
		return nbFils;
	}

	/**
	 * @param nbFils the nbFils to set
	 */
	public void setNbFils(Long nbFils) {
		this.nbFils = nbFils;
	}

	/**
	 * @return the typePrecedent
	 */
	public String getTypePrecedent() {
		return typePrecedent;
	}

	/**
	 * @param typePrecedent the typePrecedent to set
	 */
	public void setTypePrecedent(String typePrecedent) {
		this.typePrecedent = typePrecedent;
	}

	/**
	 * @return the datePrecedente
	 */
	public Date getDatePrecedente() {
		return datePrecedente;
	}

	/**
	 * @param datePrecedente the datePrecedente to set
	 */
	public void setDatePrecedente(Date datePrecedente) {
		this.datePrecedente = datePrecedente;
	}

	/**
	 * @return the valeurIndicePrecedent
	 */
	public Long getValeurIndicePrecedent() {
		return valeurIndicePrecedent;
	}

	/**
	 * @param valeurIndicePrecedent the valeurIndicePrecedent to set
	 */
	public void setValeurIndicePrecedent(Long valeurIndicePrecedent) {
		this.valeurIndicePrecedent = valeurIndicePrecedent;
	}

	/**
	 * @return the typeReleve
	 */
	public String getTypeReleve() {
		return typeReleve;
	}

	/**
	 * @param typeReleve the typeReleve to set
	 */
	public void setTypeReleve(String typeReleve) {
		this.typeReleve = typeReleve;
	}

	/**
	 * @return the dateReleve
	 */
	public Date getDateReleve() {
		return dateReleve;
	}

	/**
	 * @param dateReleve the dateReleve to set
	 */
	public void setDateReleve(Date dateReleve) {
		this.dateReleve = dateReleve;
	}

	/**
	 * @return the valeurIndice
	 */
	public Long getValeurIndice() {
		return valeurIndice;
	}

	/**
	 * @param valeurIndice the valeurIndice to set
	 */
	public void setValeurIndice(Long valeurIndice) {
		this.valeurIndice = valeurIndice;
	}

	/**
	 * @return the consommationReleve
	 */
	public Long getConsommationReleve() {
		return consommationReleve;
	}

	/**
	 * @param consommationReleve the consommationReleve to set
	 */
	public void setConsommationReleve(Long consommationReleve) {
		this.consommationReleve = consommationReleve;
	}

	/**
	 * @return the conscpt
	 */
	public Long getConscpt() {
		return conscpt;
	}

	/**
	 * @param conscpt the conscpt to set
	 */
	public void setConscpt(Long conscpt) {
		this.conscpt = conscpt;
	}

	/**
	 * @return the montantNet
	 */
	public Long getMontantNet() {
		return montantNet;
	}

	/**
	 * @param montantNet the montantNet to set
	 */
	public void setMontantNet(Long montantNet) {
		this.montantNet = montantNet;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
