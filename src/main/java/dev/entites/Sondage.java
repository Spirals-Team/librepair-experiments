package dev.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Sondage extends BaseEntite {

	@NotEmpty
	@Column(name = "titre")
	private String titre;

	@ManyToMany
	@JoinTable(name = "sondage_option_sondage", joinColumns = @JoinColumn(name = "id_sondage", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_option_sondage", referencedColumnName = "id"))
	private List<OptionSondage> options = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "classe_id")
	private Classe classe;

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public List<OptionSondage> getOptions() {
		return options;
	}

	public void setOptions(List<OptionSondage> options) {
		this.options = options;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

}
