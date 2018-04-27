package nc.noumea.mairie.bilan.energie.contract.dto;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.annotations.IdDto;


/**
 * DTO Eclairage Public
 * 
 * @author Greg Dujardin
 *
 */
public class EclairagePublic implements DtoModel {

	private static final long serialVersionUID = 1L;

	/** Version de l'entité */
	private long version;
	
	/** Identifiant technique */
	@IdDto
	private Long id;
	
	/** Désignation */
	private String designation;
	
	/** Liste des supports */
	private List<Support> listeSupport;

	/** Direction */
	private CodeLabel direction;
	
	/** Infrastructure */
	private CodeLabel infrastructure;
	
	/** Compléments */
	private String complements;
	
	/** Type de zone */
	private CodeLabel typeZone;
	
	/** Nb de km de rues éclairées */
	private Long nbKmEclaires;

	/** Numéro de poste de transformation EEC */
	private String noPoste;
	
	/** Nom du poste */
	private String nomPoste;

	/** Date de début d'existence */
	private Date dateDebut;

	/** Date de fin d'existence */
	private Date dateFin;

	/** Durée de l'amortissement */
	private Long dureeAmortissement;
	
	/** Unité de gestion */
	private CodeLabel conversion;
	
	/** Liste des polices */
	private List<Police> listePolice;

	/** Date de modification */
	private Date dateCreation;
	
	/** Auteur de la dernière modification */
	private String auteurCreation;

	/** Date de modification */
	private Date dateModif;
	
	/** Auteur de la dernière modification */
	private String auteurModif;
	
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
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	/**
	 * @return the listeSupport
	 */
	public List<Support> getListeSupport() {
		return listeSupport;
	}

	/**
	 * @param listeSupport the listeSupport to set
	 */
	public void setListeSupport(List<Support> listeSupport) {
		this.listeSupport = listeSupport;
	}

	/**
	 * @return the direction
	 */
	public CodeLabel getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(CodeLabel direction) {
		this.direction = direction;
	}

	/**
	 * @return the infrastructure
	 */
	public CodeLabel getInfrastructure() {
		return infrastructure;
	}

	/**
	 * @param infrastructure the infrastructure to set
	 */
	public void setInfrastructure(CodeLabel infrastructure) {
		this.infrastructure = infrastructure;
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
	 * @return the typeZone
	 */
	public CodeLabel getTypeZone() {
		return typeZone;
	}

	/**
	 * @param typeZone the typeZone to set
	 */
	public void setTypeZone(CodeLabel typeZone) {
		this.typeZone = typeZone;
	}

	/**
	 * @return the nbKmEclaires
	 */
	public Long getNbKmEclaires() {
		return nbKmEclaires;
	}

	/**
	 * @param nbKmEclaires the nbKmEclaires to set
	 */
	public void setNbKmEclaires(Long nbKmEclaires) {
		this.nbKmEclaires = nbKmEclaires;
	}

	/**
	 * @return the noPoste
	 */
	public String getNoPoste() {
		return noPoste;
	}

	/**
	 * @param noPoste the noPoste to set
	 */
	public void setNoPoste(String noPoste) {
		this.noPoste = noPoste;
	}

	/**
	 * @return the nomPoste
	 */
	public String getNomPoste() {
		return nomPoste;
	}

	/**
	 * @param nomPoste the nomPoste to set
	 */
	public void setNomPoste(String nomPoste) {
		this.nomPoste = nomPoste;
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
	 * @return the dureeAmortissement
	 */
	public Long getDureeAmortissement() {
		return dureeAmortissement;
	}

	/**
	 * @param dureeAmortissement the dureeAmortissement to set
	 */
	public void setDureeAmortissement(Long dureeAmortissement) {
		this.dureeAmortissement = dureeAmortissement;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the conversion
	 */
	public CodeLabel getConversion() {
		return conversion;
	}

	/**
	 * @param conversion the conversion to set
	 */
	public void setConversion(CodeLabel conversion) {
		this.conversion = conversion;
	}

	/**
	 * @return the listePolice
	 */
	public List<Police> getListePolice() {
		return listePolice;
	}

	/**
	 * @param listePolice the listePolice to set
	 */
	public void setListePolice(List<Police> listePolice) {
		this.listePolice = listePolice;
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



}
