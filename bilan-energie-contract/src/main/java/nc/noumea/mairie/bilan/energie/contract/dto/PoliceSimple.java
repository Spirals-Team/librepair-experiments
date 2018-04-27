package nc.noumea.mairie.bilan.energie.contract.dto;

import java.util.Date;

import nc.noumea.mairie.bilan.energie.contract.dto.annotations.IdDto;


/**
 * DTO Police
 * 
 * @author Greg Dujardin
 *
 */
public class PoliceSimple implements DtoModel {

	private static final long serialVersionUID = 1L;

	/** Identifiant technique */
	@IdDto
	private Long id;

	/** Version de l'entité */
	private long version;
	
	/** Numéro de la police */
	private String numPolice;
	
	/** Structure à laquelle est rattachée la police */
	private StructureLabel structure;
	
	/** Type de la police */
	private CodeLabel type;
	
	/** Intitulé de la police */
	private String intitule;
	
	/** Client rattaché à la police */
	private CodeLabel client;
	
	/** Adresse */
	private AdresseLabel adresse;
	
	/** Calibre */
	private Long calibre;
	
	/** Nombre de fils du compteur rattaché à la police */
	private Long nbFils;
	
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
	public StructureLabel getStructure() {
		return structure;
	}

	/**
	 * @param structure the structure to set
	 */
	public void setStructure(StructureLabel structure) {
		this.structure = structure;
	}

	/**
	 * @return the type
	 */
	public CodeLabel getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(CodeLabel type) {
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
	public CodeLabel getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(CodeLabel client) {
		this.client = client;
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
