package br.net.itech.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import br.net.itech.enums.JusticeTypes;

@Entity
@Table(name = "processes")
public class Process implements Serializable {
	private static final long serialVersionUID = 6301693464670926923L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "court", nullable = false)
	private String court;

	@ManyToOne(fetch = FetchType.EAGER)
	private People people;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "justiceType", nullable = false)
	private JusticeTypes justiceType;

	@Column(name = "processNumber", nullable = false)
	private String processNumber;

	@Column(name = "with_attorney", nullable = false)
	private Boolean withAttorney;

	@Column(name = "oabAttorney")
	private String oabAttorney;

	@Column(name = "confidentialProcess")
	private Boolean confidentialProcess;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "update_date")
	private Date updateDate;

	public Process() {
	}

	@PrePersist
	public void prePersist() {
		final Date currentDate = new Date();
		creationDate = currentDate;
		updateDate = currentDate;
	}

	@PreUpdate
	public void preUpdate() {
		final Date currentDate = new Date();
		updateDate = currentDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public People getPeople() {
		return people;
	}

	public void setPeople(People people) {
		this.people = people;
	}

	public String getCourt() {
		return court;
	}

	public void setCourt(String court) {
		this.court = court;
	}

	public JusticeTypes getJusticeType() {
		return justiceType;
	}

	public void setJusticeType(JusticeTypes justiceType) {
		this.justiceType = justiceType;
	}

	public String getProcessNumber() {
		return processNumber;
	}

	public void setProcessNumber(String processNumber) {
		this.processNumber = processNumber;
	}

	public Boolean getWithAttorney() {
		return withAttorney;
	}

	public void setWithAttorney(Boolean withAttorney) {
		this.withAttorney = withAttorney;
	}

	public String getOabAttorney() {
		return oabAttorney;
	}

	public void setOabAttorney(String oabAttorney) {
		this.oabAttorney = oabAttorney;
	}

	public Boolean getConfidentialProcess() {
		return confidentialProcess;
	}

	public void setConfidentialProcess(Boolean confidentialProcess) {
		this.confidentialProcess = confidentialProcess;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Process other = (Process) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Process [id=" + id + ", title=" + title + ", description=" + description + ", court=" + court
				+ ", people=" + people + ", justiceType=" + justiceType + ", processNumber=" + processNumber
				+ ", withAttorney=" + withAttorney + ", oabAttorney=" + oabAttorney + ", confidentialProcess="
				+ confidentialProcess + ", creationDate=" + creationDate + ", updateDate=" + updateDate + "]";
	}
}