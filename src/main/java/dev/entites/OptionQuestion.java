package dev.entites;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 * OptionQuestion.java
 *
 * @author matth
 */
@Entity
@Table(name = "OPTION_QUESTION")
public class OptionQuestion extends BaseEntite {

	/**
	 * libelle : String
	 */
	@Column(name = "libelle", length = 75, nullable = false, unique = false)
	@NotEmpty
	private String libelle;
	/**
	 * ok : Boolean
	 */
	@Column(name = "ok", nullable = false, unique = false)
	@NotEmpty
	private Boolean ok;

	@ManyToMany
	@JoinTable(name = "QUESTION_COMPO", joinColumns = @JoinColumn(name = "id_opt", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_que", referencedColumnName = "id"))
	private List<Question> questions;

	/**
	 * Constructor of OptionQuestion.java
	 */
	public OptionQuestion() {
		super();
	}

	/**
	 * Constructor of OptionQuestion.java
	 *
	 * @param id
	 * @param libelle
	 * @param ok
	 */
	public OptionQuestion(String id, String libelle, String ok) {
		super();
		this.setId(Long.parseLong(id));
		this.libelle = libelle;
		this.ok = Boolean.valueOf(ok);
	}

	/**
	 * Fonction :
	 *
	 * @return
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * Fonction :
	 *
	 * @param libelle
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * @return the ok
	 */
	public Boolean getOk() {
		return ok;
	}

	/**
	 * @param ok
	 *            the ok to set
	 */
	public void setOk(Boolean ok) {
		this.ok = ok;
	}

}
