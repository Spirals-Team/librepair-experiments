package dev.controller.api.sondage.viewModels;

public class ListerSondageVM {

	private long id;
	private String titre;
	private ClasseListerSondageVM classe;
	private int nb_options;

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

	public int getNb_options() {
		return nb_options;
	}

	public void setNb_options(int nb_options) {
		this.nb_options = nb_options;
	}

}
