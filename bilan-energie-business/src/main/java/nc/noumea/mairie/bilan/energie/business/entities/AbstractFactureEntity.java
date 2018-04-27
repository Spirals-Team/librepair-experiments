package nc.noumea.mairie.bilan.energie.business.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * Entity abstraite Facture 
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractFactureEntity  {
	

	/** Identifiant technique */
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "idFactureGenerator")
	@SequenceGenerator( name = "idFactureGenerator", sequenceName = "SEQ_FACTURES_ID", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	/** Fichier auquel est rattaché la facture */
	@ManyToOne
	@JoinColumn(name = "FICHIER_ID")
	private FichierFactureEntity fichierFacture;
	
	/** Code */
	@Column(name = "CODE")
	private Long code;

	/** Rgpmt */
	@Column(name = "RGPMT")
	private String rgpmt;
	
	/** Année de facturation */
	@Column(name = "ANFAC")
	private Long anneeFacturation;
	
	/** Mois de facturation */
	@Column(name = "MOISFAC")
	private Long moisFacturation;

	/** Numéro de police */
	@Column(name = "POLICE")
	private String numPolice;
	
	/** Numéro de facture */
	@Column(name = "FAC")
	private Long numFacture;
	
	/** Tarif */
	@Column(name = "TARIF")
	private String tarif;
	
	/** Puissance */
	@Column(name = "PUIS")
	private BigDecimal puissance;
	
	/** Quartier */
	@Column(name = "QUARTIER")
	private Long quartier;
	
	/** Rue */
	@Column(name = "RUE")
	private Long rue;
	
	/** Numéro dans la voie */
	@Column(name = "NUMVOI")
	private Long numVoie;
	
	/** Cptnv */
	@Column(name = "CPTNV")
	private String cptnv;
	
	/** Description */
	@Column(name = "DESCRIP")
	private String description;
	
	/** Numéro de compteur */
	@Column(name = "CPT")
	private String numCompteur;
	
	/** Nb de fils */
	@Column(name = "NBRFILS")
	private Long nbFils;
	
	/** Type de relevé précédente */
	@Column(name = "TYPPRE")
	private String typePrecedent;
	
	/** Date de relevé précédente */
	@Column(name = "DATEPRE")
	private Date datePrecedente;
	
	/** Valeur de l'indice précédent */
	@Column(name = "OLDIND")
	private Long valeurIndicePrecedent;
	
	/** Type de facturation */
	@Column(name = "TYPFAC")
	private String typeReleve;
	
	/** Date de facturation */
	@Column(name = "DATERLV")
	private Date dateReleve;
	
	/** Valeur de l'indice */
	@Column(name = "NEWIND")
	private Long valeurIndice;
	
	/** Consommation relevé */
	@Column(name = "CONSRLV")
	private Long consommationReleve;
	
	/** concpt */
	@Column(name = "CONSCPT")
	private Long conscpt;
	
	/** Montant net */
	@Column(name = "MNTNET")
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

}
