package nc.noumea.mairie.bilan.energie.business.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.envers.Audited;


/**
 * Entity abstraite 
 * 
 * @author Greg Dujardin
 */
@MappedSuperclass
@Audited
public abstract class AbstractEntity {

	@Version
	@Column(name = "VERSION")
	private long version;
	
	/** Date de modification */
	@Column(name = "DATE_CREATION")
	private Date dateCreation;
	
	/** Auteur de la dernière modification */
	@Column(name = "AUTEUR_CREATION")
	private String auteurCreation;

	
	/** Date de modification */
	@Column(name = "DATE_MODIF")
	private Date dateModif;
	
	/** Auteur de la dernière modification */
	@Column(name = "AUTEUR_MODIF")
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
