package nc.noumea.mairie.bilan.energie.contract.dto;

import java.math.BigDecimal;
import java.util.Date;

import nc.noumea.mairie.bilan.energie.contract.dto.annotations.IdDto;


/**
 * DTO Batiment simple sans les listes
 * 
 * @author Greg Dujardin
 *
 */
public class BatimentSimple implements DtoModel  {
	
	private static final long serialVersionUID = 1L;

	/** Version de l'entité */
	private long version;
	
	/** Identifiant technique */
	@IdDto
	private Long id;
	
	/** Désignation */
	private String designation;
	
	/** Direction */
	private CodeLabel direction;
	
	/** Infrastructure */
	private CodeLabel infrastructure;
	
	/** Adresse */
	private AdresseLabel adresse;
	
	/** Direction d'affectation */
	private CodeLabel directionAffectation;
	
	/** Code CTME */
	private CodeLabel codeCtme;
	
	/** Détail Type */ 
	private String detailType;
	
	/** Surface SHOB */
	private BigDecimal surfaceShob;
	
	/** Surface habitée SHON */
	private BigDecimal surfaceHabiteeShon;
	
	/** Surface arrosée */
	private Long surfaceArrosee;
	
	/** Surface éclairée */
	private Long surfaceEclairee;
	
	/** Effectif */
	private Long effectif;

	/** Date de début d'existence */
	private Date dateDebut;

	/** Date de fin d'existence */
	private Date dateFin;

	/** Durée de l'amortissement */
	private Long dureeAmortissement;
	
	/** Unité de gestion */
	private CodeLabel conversion;

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
	 * @return the directionAffectation
	 */
	public CodeLabel getDirectionAffectation() {
		return directionAffectation;
	}

	/**
	 * @param directionAffectation the directionAffectation to set
	 */
	public void setDirectionAffectation(CodeLabel directionAffectation) {
		this.directionAffectation = directionAffectation;
	}

	/**
	 * @return the codeCtme
	 */
	public CodeLabel getCodeCtme() {
		return codeCtme;
	}

	/**
	 * @param codeCtme the codeCtme to set
	 */
	public void setCodeCtme(CodeLabel codeCtme) {
		this.codeCtme = codeCtme;
	}

	/**
	 * @return the detailType
	 */
	public String getDetailType() {
		return detailType;
	}

	/**
	 * @param detailType the detailType to set
	 */
	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}

	/**
	 * @return the surfaceShob
	 */
	public BigDecimal getSurfaceShob() {
		return surfaceShob;
	}

	/**
	 * @param surfaceShob the surfaceShob to set
	 */
	public void setSurfaceShob(BigDecimal surfaceShob) {
		this.surfaceShob = surfaceShob;
	}

	/**
	 * @return the surfaceHabiteeShon
	 */
	public BigDecimal getSurfaceHabiteeShon() {
		return surfaceHabiteeShon;
	}

	/**
	 * @param surfaceHabiteeShon the surfaceHabiteeShon to set
	 */
	public void setSurfaceHabiteeShon(BigDecimal surfaceHabiteeShon) {
		this.surfaceHabiteeShon = surfaceHabiteeShon;
	}

	/**
	 * @return the surfaceArrosee
	 */
	public Long getSurfaceArrosee() {
		return surfaceArrosee;
	}

	/**
	 * @param surfaceArrosee the surfaceArrosee to set
	 */
	public void setSurfaceArrosee(Long surfaceArrosee) {
		this.surfaceArrosee = surfaceArrosee;
	}

	/**
	 * @return the surfaceEclairee
	 */
	public Long getSurfaceEclairee() {
		return surfaceEclairee;
	}

	/**
	 * @param surfaceEclairee the surfaceEclairee to set
	 */
	public void setSurfaceEclairee(Long surfaceEclairee) {
		this.surfaceEclairee = surfaceEclairee;
	}

	/**
	 * @return the effectif
	 */
	public Long getEffectif() {
		return effectif;
	}

	/**
	 * @param effectif the effectif to set
	 */
	public void setEffectif(Long effectif) {
		this.effectif = effectif;
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
