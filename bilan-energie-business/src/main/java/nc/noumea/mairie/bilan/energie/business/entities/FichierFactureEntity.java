package nc.noumea.mairie.bilan.energie.business.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nc.noumea.mairie.bilan.energie.contract.enumeration.EtatFichier;

/**
 * Entity Fichier Facture
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Table(name="FICHIER_FACTURE")
public class FichierFactureEntity extends AbstractEntity {
	
	/** Identifiant technique */
	@Id
	@GeneratedValue(
	        strategy=GenerationType.SEQUENCE, 
	        generator="idFichierFactureGenerator")
	    @SequenceGenerator(
	        name="idFichierFactureGenerator",
	        sequenceName="SEQ_FICHIER_FACTURE_ID",
	        allocationSize=1
	    )
	@Column(name = "ID")
	private Long id;
	
	/** Liste des factures du fichier */
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "fichierFacture")
	private List<AbstractFactureEntity> listeFacture;
	
	/** Liste des anomalies du fichier */
	@OneToMany(cascade=CascadeType.ALL,  mappedBy = "fichierFacture")
	private List<FichierAnomalieEntity> listeAnomalie;
	
	
	/** Nom du fichier */
	@Column(name = "NOM")
	private String nom;

	/** MD5 du fichier */
	@Column(name = "md5")
	private String md5;
	
	/** Taille du fichier */
	@Column(name = "taille")
	private Integer taille;
	
	/** Date d'import */
	@Column(name = "DATE_IMPORT")
	private Date dateImport;
	
	/** Date d'intégration */
	@Column(name = "DATE_INTEGRATION")
	private Date dateIntegration;
	
	/** Nombre de lignes */
	@Column(name = "NB_LIGNES")
	private Integer nbLignes;
	
	/** Nombre de lignes en erreurs */
	@Column(name = "NB_ERREURS")
	private Integer nbErreurs;
	
	/** Nombre d'erreurs traitées */
	@Column(name = "NB_ERREURS_TRAITEES")
	private Integer nbErreursTraitees;
	
	/** Etat */
	@Column(name = "ETAT")
	@Enumerated(EnumType.STRING)
	private EtatFichier etat;
	
	/** Detail de l'erreur */
	@Column(name = "DETAIL_ERREUR")
	private String detailErreur;

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
	 * @return the listeFacture
	 */
	public List<AbstractFactureEntity> getListeFacture() {
		return listeFacture;
	}

	/**
	 * @param listeFacture the listeFacture to set
	 */
	public void setListeFacture(List<AbstractFactureEntity> listeFacture) {
		this.listeFacture = listeFacture;
	}

	/**
	 * @return the listeAnomalie
	 */
	public List<FichierAnomalieEntity> getListeAnomalie() {
		return listeAnomalie;
	}

	/**
	 * @param listeAnomalie the listeAnomalie to set
	 */
	public void setListeAnomalie(List<FichierAnomalieEntity> listeAnomalie) {
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
	
}
