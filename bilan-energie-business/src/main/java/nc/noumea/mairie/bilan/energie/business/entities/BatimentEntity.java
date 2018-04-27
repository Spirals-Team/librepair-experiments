package nc.noumea.mairie.bilan.energie.business.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 * Entity Batiment
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Audited
@Table(name="BATIMENT")
public class BatimentEntity extends StructureEntity {
	
	/** Adresse */
	@OneToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "ADRESSE_ID")
	private AdresseEntity adresse;
	
	/** Direction d'affectation */
	@OneToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "DIRECTION_AFFECTATION_ID")
	private DirectionEntity directionAffectation;
	
	/** Code CTME */
	@OneToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "CODE_CTME_ID")
	private CodeCtmeEntity codeCtme;
	
	/** Détail Type */ 
	@Column(name = "DETAIL_TYPE")
	private String detailType;
	
	/** Surface SHOB */
	@Column(name = "SURFACE_SHOB")
	private BigDecimal surfaceShob;
	
	/** Surface habitée SHON */
	@Column(name = "SURFACE_HABITEE_SHON")
	private BigDecimal surfaceHabiteeShon;
	
	/** Surface arrosée */
	@Column(name = "SURFACE_ARROSEE")
	private Long surfaceArrosee;
	
	/** Surface éclairée */
	@Column(name = "SURFACE_ECLAIREE")
	private Long surfaceEclairee;
	
	/** Effectif */
	@Column(name = "EFFECTIF")
	private Long effectif;

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
	 * @return the directionAffectation
	 */
	public DirectionEntity getDirectionAffectation() {
		return directionAffectation;
	}

	/**
	 * @param directionAffectation the directionAffectation to set
	 */
	public void setDirectionAffectation(DirectionEntity directionAffectation) {
		this.directionAffectation = directionAffectation;
	}

	/**
	 * @return the codeCtme
	 */
	public CodeCtmeEntity getCodeCtme() {
		return codeCtme;
	}

	/**
	 * @param codeCtme the codeCtme to set
	 */
	public void setCodeCtme(CodeCtmeEntity codeCtme) {
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
	
}
