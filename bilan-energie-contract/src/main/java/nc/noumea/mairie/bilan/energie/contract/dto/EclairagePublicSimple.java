package nc.noumea.mairie.bilan.energie.contract.dto;

import java.util.Date;

import nc.noumea.mairie.bilan.energie.contract.dto.annotations.IdDto;

/**
 * DTO Eclairage Public Simple sans ses supports
 * 
 * @author Greg Dujardin
 *
 */
public class EclairagePublicSimple implements DtoModel {

	private static final long serialVersionUID = 1L;

	/** Identifiant technique */
	@IdDto
	private Long id;
	
	/** Version de l'entité */
	private long version;
	
	/** Direction */
	private CodeLabel direction;
	
	/** Infrastructure */
	private CodeLabel infrastructure;
	
	/** Compléments */
	private String complements;
	
	/** Type de Zone */
	private CodeLabel typeZone;
	
	/** Nb de km de rues éclairées */
	private Long nbKmEclaires;

	/** Numéro de poste de transformation EEC */
	private String noPoste;

	/** Date de début d'existence */
	private Date dateDebut;

	/** Date de fin d'existence */
	private Date dateFin;

	/** Désignation */
	private String designation;

	/** Durée de l'amortissement */
	private Long dureeAmortissement;
	
	/** Unité de gestion */
	private CodeLabel conversion;
	
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}
