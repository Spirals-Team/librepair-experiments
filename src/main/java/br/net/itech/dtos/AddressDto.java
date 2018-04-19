package br.net.itech.dtos;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class AddressDto {
	private String id;

	@NotEmpty(message = "Rua não pode ser vazia.")
	@Length(min = 5, max = 25, message = "Rua deve conter de 5 a 25 caracteres.")
	private String street;

	@NotEmpty(message = "Bairro não pode ser vazia.")
	@Length(min = 3, max = 20, message = "Bairro deve conter de 3 a 20 caracteres.")
	private String district;

	@NotEmpty(message = "Número não pode ser vazio.")
	@Length(min = 1, max = 5, message = "Rua deve conter de 1 a 5 caracteres.")
	private String number;

	private Optional<String> complement = Optional.empty();

	@Valid
	private CityDto city;

	public AddressDto() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Optional<String> getComplement() {
		return complement;
	}

	public void setComplement(Optional<String> complement) {
		this.complement = complement;
	}

	public CityDto getCity() {
		return city;
	}

	public void setCity(CityDto city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "AddressDto [id=" + id + ", street=" + street + ", district=" + district + ", number=" + number
				+ ", complement=" + complement + ", city=" + city + "]";
	}
}