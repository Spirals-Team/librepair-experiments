package nc.noumea.mairie.bilan.energie.business.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 * Entity Eclairage Public
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Audited
@Table(name="ECLAIRAGE_PUBLIC")
public class EclairagePublicEntity extends StructureEntity {
	
	/** Compléments */
	@Column(name="COMPLEMENTS")
	private String complements;

	/** Type de zone */
	@OneToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "TYPE_ZONE_ID")
	private TypeZoneEntity typeZone;

	/** Nb de km de rues éclairées */
	@Column(name="NB_KM_ECLAIRES")
	private Long nbKmEclaires;
	
	/** Numéro de poste de transformation EEC */
	@Column(name="NO_POSTE")
	private String noPoste;
	
	/** Nom de poste de transformation EEC */
	@Column(name="NOM_POSTE")
	private String nomPoste;
	
	/** Liste des supports de l'EP */
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true, mappedBy = "eclairagePublic")
	private List<SupportEntity> listeSupport;

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
	public TypeZoneEntity getTypeZone() {
		return typeZone;
	}

	/**
	 * @param typeZone the typeZone to set
	 */
	public void setTypeZone(TypeZoneEntity typeZone) {
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
	 * @return the listeSupport
	 */
	public List<SupportEntity> getListeSupport() {
		return listeSupport;
	}

	/**
	 * @param listeSupport the listeSupport to set
	 */
	public void setListeSupport(List<SupportEntity> listeSupport) {
		this.listeSupport = listeSupport;
	}
}
