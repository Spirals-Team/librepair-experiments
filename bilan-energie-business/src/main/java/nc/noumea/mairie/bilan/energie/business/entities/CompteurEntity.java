package nc.noumea.mairie.bilan.energie.business.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity Compteur
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Table(name="COMPTEUR")
public class CompteurEntity extends AbstractEntity{
	
	/** Identifiant technique */
	@Id
	@GeneratedValue(
	        strategy=GenerationType.SEQUENCE, 
	        generator="idCompteurGenerator")
	    @SequenceGenerator(
	        name="idCompteurGenerator",
	        sequenceName="SEQ_COMPTEUR_ID",
	        allocationSize=1
	    )
	@Column(name = "ID")
	private Long id;
	
	/** Numéro du compteur */
	@Column(name = "NUM_COMPTEUR")
	private String numCompteur;
	
	/** Police à laquelle est rattaché le compteur */
	@ManyToOne
	@JoinColumn(name = "POLICE_ID")
	private PoliceEntity police;
	
	/** Liste des comptages du compteur */
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true, mappedBy = "compteur")
	private List<ComptageEntity> listeComptage;

	/** Adresse */
	@OneToOne
	@JoinColumn(name = "ADRESSE_ID")
	private AdresseEntity adresse;

	/** Compteur père */
	@OneToOne
	@JoinColumn(name = "COMPTEUR_PERE_ID")
	private CompteurEntity compteurPere;
	
	/** Type de compteur */
	@OneToOne
	@JoinColumn(name = "TYPE_COMPTEUR_ID")
	private TypeCompteurEntity type;

	/** Type d'emplacement */
	@OneToOne
	@JoinColumn(name = "TYPE_EMPLACEMENT_ID")
	private TypeEmplacementEntity typeEmplacement;

	/** Date de début d'existence */
	@Column(name = "DATE_DEBUT")
	private Date dateDebut;
	
	/** Date de fin d'existence */
	@Column(name = "DATE_FIN")
	private Date dateFin;

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
	 * @return the police
	 */
	public PoliceEntity getPolice() {
		return police;
	}

	/**
	 * @param police the police to set
	 */
	public void setPolice(PoliceEntity police) {
		this.police = police;
	}

	/**
	 * @return the listeComptage
	 */
	public List<ComptageEntity> getListeComptage() {
		return listeComptage;
	}

	/**
	 * @param listeComptage the listeComptage to set
	 */
	public void setListeComptage(List<ComptageEntity> listeComptage) {
		this.listeComptage = listeComptage;
	}

	/**
	 * @return the adresse
	 */
	public AdresseEntity getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(AdresseEntity adresse) {
		this.adresse = adresse;
	}

	/**
	 * @return the compteurPere
	 */
	public CompteurEntity getCompteurPere() {
		return compteurPere;
	}

	/**
	 * @param compteurPere the compteurPere to set
	 */
	public void setCompteurPere(CompteurEntity compteurPere) {
		this.compteurPere = compteurPere;
	}

	/**
	 * @return the type
	 */
	public TypeCompteurEntity getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TypeCompteurEntity type) {
		this.type = type;
	}

	/**
	 * @return the typeEmplacement
	 */
	public TypeEmplacementEntity getTypeEmplacement() {
		return typeEmplacement;
	}

	/**
	 * @param typeEmplacement the typeEmplacement to set
	 */
	public void setTypeEmplacement(TypeEmplacementEntity typeEmplacement) {
		this.typeEmplacement = typeEmplacement;
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

	
}
