package br.net.itech.dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class CityDto {
	private String id;

	@NotEmpty(message = "Cidade não pode ser vazio.")
	@Length(min = 3, max = 30, message = "Cidade deve conter entre 3 a 30 caracteres.")
	private String name;
	
	@Valid
	private ProvinceDto province;
	
	public CityDto() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProvinceDto getProvince() {
		return province;
	}

	public void setProvince(ProvinceDto province) {
		this.province = province;
	}

	@Override
	public String toString() {
		return "CityDto [id=" + id + ", name=" + name + ", province=" + province + "]";
	}
}