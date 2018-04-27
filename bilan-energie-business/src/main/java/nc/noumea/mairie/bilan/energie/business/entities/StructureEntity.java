package nc.noumea.mairie.bilan.energie.business.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 * Entity abstraite Structure
 * 
 * @author Greg Dujardin
 */
@Entity
@Audited
@Table(name = "STRUCTURE")
@Inheritance(strategy=InheritanceType.JOINED)
public class StructureEntity extends AbstractEntity{

	/** Identifiant technique */
	@Id
	@GeneratedValue(
	        strategy=GenerationType.SEQUENCE, 
	        generator="idStructureGenerator")
	    @SequenceGenerator(
	        name="idStructureGenerator",
	        sequenceName="SEQ_STRUCTURE_ID",
	        allocationSize=1
	    )
	@Column(name = "ID")
	private Long id;
	
	/** Infrastructure */
	@ManyToOne
	@JoinColumn(name = "INFRASTRUCTURE_ID")
	private InfrastructureEntity infrastructure;
	
	/** Désignation */
	@Column(name = "DESIGNATION")
	private String designation;
	
	/** Direction */
	@OneToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "DIRECTION_ID")
	private DirectionEntity direction;
	
	/** Durée de l'amortissement */
	@Column(name = "DUREE_AMORTISSEMENT")
	private Long dureeAmortissement;
	
	/** Unité de gestion */
	@OneToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "CONVERSION_ID")
	private ConversionEntity conversion;
	
	/** Liste des polices de la structure */
	@NotAudited
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true, mappedBy = "structure")
	private List<PoliceEntity> listePolice;
	
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
	 * @return the infrastructure
	 */
	public InfrastructureEntity getInfrastructure() {
		return infrastructure;
	}

	/**
	 * @param infrastructure the infrastructure to set
	 */
	public void setInfrastructure(InfrastructureEntity infrastructure) {
		this.infrastructure = infrastructure;
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
	public DirectionEntity getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(DirectionEntity direction) {
		this.direction = direction;
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
	public ConversionEntity getConversion() {
		return conversion;
	}

	/**
	 * @param conversion the conversion to set
	 */
	public void setConversion(ConversionEntity conversion) {
		this.conversion = conversion;
	}

	/**
	 * @return the listePolice
	 */
	public List<PoliceEntity> getListePolice() {
		return listePolice;
	}

	/**
	 * @param listePolice the listePolice to set
	 */
	public void setListePolice(List<PoliceEntity> listePolice) {
		this.listePolice = listePolice;
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
