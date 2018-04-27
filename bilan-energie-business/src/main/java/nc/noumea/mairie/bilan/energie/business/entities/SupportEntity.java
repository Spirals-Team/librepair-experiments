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

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 * Entity Support
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Audited
@Table(name="SUPPORT")
public class SupportEntity extends AbstractEntity{
	
	/** Identifiant technique */
	@Id
	@GeneratedValue(
	        strategy=GenerationType.SEQUENCE, 
	        generator="idSupportGenerator")
	    @SequenceGenerator(
	        name="idSupportGenerator",
	        sequenceName="SEQ_SUPPORT_ID",
	        allocationSize=1
	    )
	@Column(name = "ID")
	private Long id;
	
	/** Eclairage Public auquel est rattaché le support */
	@ManyToOne
	@JoinColumn(name = "ECLAIRAGE_PUBLIC_ID")
	private EclairagePublicEntity eclairagePublic;
	
	/** Liste des luminaires de l'EP */
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true, mappedBy = "support")
	private List<LuminaireEntity> listeLuminaire;

	/** Adresse */
	@OneToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "ADRESSE_ID")
	private AdresseEntity adresse;
	
	/** Type de support */
	@OneToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "TYPE_SUPPORT_ID")
	private TypeSupportEntity type;
	
	/** Support utilisé pour la distribution d'électricité */
	@Column(name = "MIXTE_DISTRI")
	private boolean mixteDistri;
	
	/** Modèle de support */
	@Column(name = "MODELE_SUPPORT")
	private String modele;
	
	/** Numéro dans l'inventaire EEC */
	@Column(name = "NUM_INVENTAIRE")
	private String numInventaire;
	
	/** Propriétaire du support */
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
	 * @return the eclairagePublic
	 */
	public EclairagePublicEntity getEclairagePublic() {
		return eclairagePublic;
	}

	/**
	 * @param eclairagePublic the eclairagePublic to set
	 */
	public void setEclairagePublic(EclairagePublicEntity eclairagePublic) {
		this.eclairagePublic = eclairagePublic;
	}

	/**
	 * @return the listeLuminaire
	 */
	public List<LuminaireEntity> getListeLuminaire() {
		return listeLuminaire;
	}

	/**
	 * @param listeLuminaire the listeLuminaire to set
	 */
	public void setListeLuminaire(List<LuminaireEntity> listeLuminaire) {
		this.listeLuminaire = listeLuminaire;
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
	 * @return the type
	 */
	public TypeSupportEntity getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TypeSupportEntity type) {
		this.type = type;
	}

	/**
	 * @return the mixteDistri
	 */
	public boolean isMixteDistri() {
		return mixteDistri;
	}

	/**
	 * @param mixteDistri the mixteDistri to set
	 */
	public void setMixteDistri(boolean mixteDistri) {
		this.mixteDistri = mixteDistri;
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
	 * @return the numInventaire
	 */
	public String getNumInventaire() {
		return numInventaire;
	}

	/**
	 * @param numInventaire the numInventaire to set
	 */
	public void setNumInventaire(String numInventaire) {
		this.numInventaire = numInventaire;
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
