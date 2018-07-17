package dev.entites;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "option_sondage")
public class OptionSondage extends BaseEntite {

	@NotEmpty
	@Size(min = 3)
	@Column(name = "libelle")
	private String libelle;
	@NotEmpty
	@Size(min = 3)
	@Column(name = "description")
	private String description;

	@ManyToMany
	@JoinTable(name = "sondage_option_sondage", joinColumns = @JoinColumn(name = "id_option_sondage", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_sondage", referencedColumnName = "id"))
	private List<Sondage> sondages;

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}