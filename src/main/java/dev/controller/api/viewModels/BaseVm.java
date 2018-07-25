package dev.controller.api.viewModels;

public class BaseVm {
	
	private Long id;
	
	public BaseVm(){
		
	}
	
	public BaseVm(Long id){
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
