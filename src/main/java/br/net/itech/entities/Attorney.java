package br.net.itech.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import br.net.itech.enums.AttorneyStatus;

@Entity
@Table(name = "attorneys")
public class Attorney implements Serializable {
	private static final long serialVersionUID = 3936655604258103956L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(mappedBy = "attorney", fetch = FetchType.LAZY)
	private People people;

	@OneToMany(mappedBy = "attorney", fetch = FetchType.LAZY)
	List<Diagnosis> diagnostics;

	@Column(name = "oab", nullable = false)
	private String oab;

	@Column(name = "vip")
	private Boolean vip;

	@Column(name = "score")
	private Integer score;

	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private AttorneyStatus status;

	@Column(name = "ban")
	private Boolean ban;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "update_date")
	private Date updateDate;

	public Attorney() {
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

	public People getPeople() {
		return people;
	}

	public void setPeople(People people) {
		this.people = people;
	}

	public List<Diagnosis> getDiagnostics() {
		return diagnostics;
	}

	public void setDiagnostics(List<Diagnosis> diagnostics) {
		this.diagnostics = diagnostics;
	}

	public String getOab() {
		return oab;
	}

	public void setOab(String oab) {
		this.oab = oab;
	}

	public Boolean getVip() {
		return vip;
	}

	public void setVip(Boolean vip) {
		this.vip = vip;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public AttorneyStatus getStatus() {
		return status;
	}

	public void setStatus(AttorneyStatus status) {
		this.status = status;
	}

	public Boolean getBan() {
		return ban;
	}

	public void setBan(Boolean ban) {
		this.ban = ban;
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
		Attorney other = (Attorney) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Attorney [id=" + id + ", oab=" + oab + ", vip=" + vip + ", score=" + score + ", status=" + status
				+ ", ban=" + ban + ", creationDate=" + creationDate + ", updateDate=" + updateDate + "]";
	}
}