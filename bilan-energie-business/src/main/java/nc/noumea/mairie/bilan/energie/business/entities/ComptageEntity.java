package nc.noumea.mairie.bilan.energie.business.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity Comptage
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Table(name="COMPTAGE")
public class ComptageEntity extends AbstractEntity{
	
	/** Identifiant technique */
	@Id
	@GeneratedValue(
	        strategy=GenerationType.SEQUENCE, 
	        generator="idComptageGenerator")
	    @SequenceGenerator(
	        name="idComptageGenerator",
	        sequenceName="SEQ_COMPTAGE_ID",
	        allocationSize=1
	    )
	@Column(name = "ID")
	private Long id;
	
	/** Compteur auquel est rattaché le comptage */
	@ManyToOne
	@JoinColumn(name = "COMPTEUR_ID")
	private CompteurEntity compteur;
	
	/** Numéro de facturation */
	@Column(name = "NUM_FACTURE")
	private Long numFacture;

	/** Année de facturation */
	@Column(name = "ANNEE")
	private Long annee;
	
	/** Mois de facturation */
	@Column(name = "MOIS")
	private Long mois;
	
	/** Type de facturation */
	@Column(name = "TYPE")
	private String type;
	
	/** Valeur de de l'index */
	@Column(name = "VALEUR")
	private Long valeur;

	/** Date du relevé */
	@Column(name = "DATE")
	private Date date;
	
	/** Montant net */
	@Column(name = "MONTANT_NET")
	private Long montantNet;

	/** Consommation relevé */
	@Column(name = "CONSOMMATION")
	private Long consommation;
	
	/** Bordereau */
	@Column(name = "BORDEREAU")
	private String bordereau;

	/** Type de facturation précédent */
	@Column(name = "TYPE_PRECEDENT")
	private String typePrecedent;
	
	/** Date du relevé précédent */
	@Column(name = "DATE_PRECEDENT")
	private Date datePrecedent;

	/** Valeur de de l'index précédent */
	@Column(name = "VALEUR_PRECEDENT")
	private Long valeurPrecedent;

	/** Consommation des compteurs divisionnaires */
	@Column(name = "CONS_CPT_DIV")
	private Long consommationCptDivisionnaire;
	
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
	 * @return the compteur
	 */
	public CompteurEntity getCompteur() {
		return compteur;
	}

	/**
	 * @param compteur the compteur to set
	 */
	public void setCompteur(CompteurEntity compteur) {
		this.compteur = compteur;
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
	 * @param annee the annee to set
	 */
	public void setAnnee(Long annee) {
		this.annee = annee;
	}

	/**
	 * @return the mois
	 */
	public Long getMois() {
		return mois;
	}

	/**
	 * @param mois the mois to set
	 */
	public void setMois(Long mois) {
		this.mois = mois;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the valeur
	 */
	public Long getValeur() {
		return valeur;
	}

	/**
	 * @param valeur the valeur to set
	 */
	public void setValeur(Long valeur) {
		this.valeur = valeur;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the consommation
	 */
	public Long getConsommation() {
		return consommation;
	}

	/**
	 * @param consommation the consommation to set
	 */
	public void setConsommation(Long consommation) {
		this.consommation = consommation;
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
	 * @return the bordereau
	 */
	public String getBordereau() {
		return bordereau;
	}

	/**
	 * @param bordereau the bordereau to set
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
	 * @param typePrecedent the typePrecedent to set
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
	 * @param datePrecedent the datePrecedent to set
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
	 * @param valeurPrecedent the valeurPrecedent to set
	 */
	public void setValeurPrecedent(Long valeurPrecedent) {
		this.valeurPrecedent = valeurPrecedent;
	}

	/**
	 * @return the consommationCptDivisionnaire
	 */
	public Long getConsommationCptDivisionnaire() {
		return consommationCptDivisionnaire;
	}

	/**
	 * @param consommationCptDivisionnaire the consommationCptDivisionnaire to set
	 */
	public void setConsommationCptDivisionnaire(Long consommationCptDivisionnaire) {
		this.consommationCptDivisionnaire = consommationCptDivisionnaire;
	}
 	
}
