package dev.controller.api.stagiaire;

public class BaseEntiteVm {

	private Long id;

	public BaseEntiteVm() {
		super();
	}

	public BaseEntiteVm(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
