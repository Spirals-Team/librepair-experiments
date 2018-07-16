package dev.entites;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

public class Sondage extends BaseEntite {

	@NotEmpty
	private String titre;
	private List<OptionSondage> options = new ArrayList<>();
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
