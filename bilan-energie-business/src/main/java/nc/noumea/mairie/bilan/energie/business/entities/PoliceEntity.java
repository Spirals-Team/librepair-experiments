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
 * Entity Police
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Table(name="POLICE")
public class PoliceEntity extends AbstractEntity{
	
	/** Identifiant technique */
	@Id
	@GeneratedValue(
	        strategy=GenerationType.SEQUENCE, 
	        generator="idPoliceGenerator")
	    @SequenceGenerator(
	        name="idPoliceGenerator",
	        sequenceName="SEQ_POLICE_ID",
	        allocationSize=1
	    )
	@Column(name = "ID")
	private Long id;
	
	/** Numéro de la police */
	@Column(name = "NUM_POLICE")
	private String numPolice;
	
	/** Structure à laquelle est rattachée la police */
	@ManyToOne
	@JoinColumn(name = "STRUCTURE_ID")
	private StructureEntity structure;
	
	/** Liste des compteurs de la police */
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true, mappedBy = "police")
	private List<CompteurEntity> listeCompteur;

	/** Type de police */
	@OneToOne
	@JoinColumn(name = "TYPE_POLICE_ID")
	private TypePoliceEntity type;

	/** Intitulé de la Police */
	@Column(name = "INTITULE")
	private String intitule;
	
	/** Client de la Police */
	@OneToOne
	@JoinColumn(name = "CLIENT_ID")
	private ClientEntity client;
	
	/** Compléments */
	@Column(name="COMPLEMENTS")
	private String complements;

	/** Adresse */
	@OneToOne
	@JoinColumn(name = "ADRESSE_ID")
	private AdresseEntity adresse;
	
	/** Calibre */
	@Column(name = "CALIBRE")
	private Long calibre;
	
	/** Nombre de fils */
	@Column(name = "NB_FILS")
	private Long nbFils;
	
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
	 * @return the structure
	 */
	public StructureEntity getStructure() {
		return structure;
	}

	/**
	 * @param structure the structure to set
	 */
	public void setStructure(StructureEntity structure) {
		this.structure = structure;
	}

	/**
	 * @return the listeCompteur
	 */
	public List<CompteurEntity> getListeCompteur() {
		return listeCompteur;
	}

	/**
	 * @param listeCompteur the listeCompteur to set
	 */
	public void setListeCompteur(List<CompteurEntity> listeCompteur) {
		this.listeCompteur = listeCompteur;
	}

	/**
	 * @return the type
	 */
	public TypePoliceEntity getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TypePoliceEntity type) {
		this.type = type;
	}

	/**
	 * @return the intitule
	 */
	public String getIntitule() {
		return intitule;
	}

	/**
	 * @param intitule the intitule to set
	 */
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	/**
	 * @return the client
	 */
	public ClientEntity getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(ClientEntity client) {
		this.client = client;
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
	 * @return the calibre
	 */
	public Long getCalibre() {
		return calibre;
	}

	/**
	 * @param calibre the calibre to set
	 */
	public void setCalibre(Long calibre) {
		this.calibre = calibre;
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
