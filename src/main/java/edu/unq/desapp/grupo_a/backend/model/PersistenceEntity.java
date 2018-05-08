package edu.unq.desapp.grupo_a.backend.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import edu.unq.desapp.grupo_a.backend.utils.JSONDateDeserialize;
import edu.unq.desapp.grupo_a.backend.utils.JSONDateSerialize;

@MappedSuperclass
public class PersistenceEntity implements Serializable {

	private static final long serialVersionUID = 4056818895685613967L;

	// Instance Variables
	@Id
	@Column(unique = true)
	@GeneratedValue(strategy = GenerationType.TABLE)
	protected Long id;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@JsonDeserialize(using = JSONDateDeserialize.class)
	@JsonSerialize(using = JSONDateSerialize.class)
	protected Date creationDate = new Date();

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
