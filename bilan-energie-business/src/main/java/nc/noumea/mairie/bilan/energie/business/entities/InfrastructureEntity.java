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

import org.hibernate.envers.Audited;

import nc.noumea.mairie.bilan.energie.contract.enumeration.TypeStructure;

/**
 * Entity Infrastructure
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Audited
@Table(name="INFRASTRUCTURE")
public class InfrastructureEntity extends AbstractEntity {
	
	/** Identifiant technique */
	@Id
	@GeneratedValue(
	        strategy=GenerationType.SEQUENCE, 
	        generator="idInfrastructureGenerator")
	    @SequenceGenerator(
	        name="idInfrastructureGenerator",
	        sequenceName="SEQ_INFRASTRUCTURE_ID",
	        allocationSize=1
	    )
	@Column(name = "ID")
	private Long id;
	
	/** Type de l'infrastructure */
	@Column(name = "TYPE")
	@Enumerated(EnumType.STRING)
	private TypeStructure type;
	
	/** Désignation */
	@Column(name = "DESIGNATION")
	private String designation;

	/** Liste des structures de l'infra */
	@OneToMany(cascade=CascadeType.REFRESH, orphanRemoval = true, mappedBy = "infrastructure")
	private List<StructureEntity> listeStructure;

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
	 * @return the type
	 */
	public TypeStructure getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TypeStructure type) {
		this.type = type;
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
	 * @return the listeStructure
	 */
	public List<StructureEntity> getListeStructure() {
		return listeStructure;
	}

	/**
	 * @param listeStructure the listeStructure to set
	 */
	public void setListeStructure(List<StructureEntity> listeStructure) {
		this.listeStructure = listeStructure;
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
