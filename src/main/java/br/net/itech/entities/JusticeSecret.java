package br.net.itech.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "justice_secret")
public class JusticeSecret {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "justice_secret", nullable = false)
	private String justiceSecret;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Process process;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "update_date")
	private Date updateDate;

	public JusticeSecret() {
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

	public String getJusticeSecret() {
		return justiceSecret;
	}

	public void setJusticeSecret(String justiceSecret) {
		this.justiceSecret = justiceSecret;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
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
		JusticeSecret other = (JusticeSecret) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JusticeSecret [id=" + id + ", justiceSecret=" + justiceSecret + ", creationDate=" + creationDate
				+ ", updateDate=" + updateDate + "]";
	}
}