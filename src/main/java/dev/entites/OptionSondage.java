package dev.entites;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class OptionSondage extends BaseEntite {

	@NotEmpty
	@Size(min = 3)
	private String libelle;
	@NotEmpty
	@Size(min = 3)
	private String description;

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
