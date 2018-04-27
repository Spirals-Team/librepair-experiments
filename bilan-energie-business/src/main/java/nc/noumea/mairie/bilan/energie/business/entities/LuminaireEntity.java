package nc.noumea.mairie.bilan.energie.business.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 * Entity Luminaire
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Audited
@Table(name="LUMINAIRE")
public class LuminaireEntity extends AbstractEntity{
	
	/** Identifiant technique */
	@Id
	@GeneratedValue(
	        strategy=GenerationType.SEQUENCE, 
	        generator="idLuminaireGenerator")
	    @SequenceGenerator(
	        name="idLuminaireGenerator",
	        sequenceName="SEQ_LUMINAIRE_ID",
	        allocationSize=1
	    )
	@Column(name = "ID")
	private Long id;
	
	/** Support du luminaire */
	@ManyToOne
	@JoinColumn(name = "SUPPORT_ID")
	private SupportEntity support;

	/** Puissance nominale du luminaire */
	@Column(name = "PUISSANCE")
	private long puissance;
	
	/** Type de source */
	@OneToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name ="TYPE_SOURCE_ID")
	private TypeSourceEntity typeSource;
	
	/** Modèle du luminaire */
	@Column(name = "MODELE")
	private String modele;
	
	/** Luminaire avec gradation */
	@Column(name = "GRADATION")
	private boolean gradation;
	
	/** Hauteur du luminaire */
	@Column(name = "HAUTEUR")
	private Long hauteur;
	
	/** Compléments */
	@Column(name = "COMPLEMENTS")
	private String complements;
	
	/** Propriétaire du luminaire */
	@OneToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "CLIENT_ID")
	private ClientEntity proprietaire;
	
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
	 * @return the support
	 */
	public SupportEntity getSupport() {
		return support;
	}

	/**
	 * @param support the support to set
	 */
	public void setSupport(SupportEntity support) {
		this.support = support;
	}

	/**
	 * @return the puissance
	 */
	public long getPuissance() {
		return puissance;
	}

	/**
	 * @param puissance the puissance to set
	 */
	public void setPuissance(long puissance) {
		this.puissance = puissance;
	}

	/**
	 * @return the Complements
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
	 * @return the modele
	 */
	public String getModele() {
		return modele;
	}

	/**
	 * @param modele the modele to set
	 */
	public void setModele(String modele) {
		this.modele = modele;
	}

	/**
	 * @return the gradation
	 */
	public boolean isGradation() {
		return gradation;
	}

	/**
	 * @param gradation the gradation to set
	 */
	public void setGradation(boolean gradation) {
		this.gradation = gradation;
	}

	/**
	 * @return the hauteur
	 */
	public Long getHauteur() {
		return hauteur;
	}

	/**
	 * @param hauteur the hauteur to set
	 */
	public void setHauteur(Long hauteur) {
		this.hauteur = hauteur;
	}

	/**
	 * @return the typeSource
	 */
	public TypeSourceEntity getTypeSource() {
		return typeSource;
	}

	/**
	 * @param typeSource the typeSource to set
	 */
	public void setTypeSource(TypeSourceEntity typeSource) {
		this.typeSource = typeSource;
	}

	/**
	 * @return the proprietaire
	 */
	public ClientEntity getProprietaire() {
		return proprietaire;
	}

	/**
	 * @param proprietaire the proprietaire to set
	 */
	public void setProprietaire(ClientEntity proprietaire) {
		this.proprietaire = proprietaire;
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
