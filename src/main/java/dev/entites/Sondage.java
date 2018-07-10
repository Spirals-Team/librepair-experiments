package dev.entites;

import java.util.ArrayList;
import java.util.List;

public class Sondage extends BaseEntite {

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
