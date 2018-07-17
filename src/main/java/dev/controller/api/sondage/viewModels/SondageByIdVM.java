package dev.controller.api.sondage.viewModels;

import java.util.List;

public class SondageByIdVM {

	private long id;
	private String titre;
	private ClasseListerSondageVM classe;
	private List<OptionListerSondageVM> options;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public ClasseListerSondageVM getClasse() {
		return classe;
	}

	public void setClasse(ClasseListerSondageVM classe) {
		this.classe = classe;
	}

	public List<OptionListerSondageVM> getOptions() {
		return options;
	}

	public void setOptions(List<OptionListerSondageVM> options) {
		this.options = options;
	}

}
