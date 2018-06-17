package com.les.brouilles.planner.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "POST_IT")
public class PostIT {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_POSTIT")
	@SequenceGenerator(sequenceName = "SEQ_POSTIT", allocationSize = 1, name = "SEQ_POSTIT")
	private Long id;

	@NotNull
	@Column(name = "NOM", length = 100, nullable = false)
	private String nom;

	@NotNull
	@Column(name = "DATE_PI", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date debut;

	@PrePersist
	public void beforePersist() {
		// Hack pour ne pas avoir de date de debut à 00:00:00 (qui exclut des
		// résultats lors de la query en base)
		if (null != debut && debut.getHours() == 0) {
			debut.setSeconds(1);
		}
	}

}
