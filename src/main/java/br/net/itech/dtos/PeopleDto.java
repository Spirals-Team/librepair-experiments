package br.net.itech.dtos;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

public class PeopleDto {
	private String id;
	
	@NotEmpty(message = "Nome não pode ser vazio")
	@Length(min = 5, max = 255, message = "O nome deve conter no mínimo 5 caracteres, e no máximo 255.")
	private String name;
	
	@Email(message = "Email invalido")
	@NotEmpty(message = "Email não pode ser vazio.")
	private String email;
	
	@NotEmpty(message = "CPF não pode ser vazio.")
	@CPF(message = "CPF inválido")
	private String cpf;
	
	private String registry;

	@NotEmpty(message = "RG não pode ser vazio.")
	@Length(min = 10, max = 10, message = "RG inválido")
	private String rg;
	
	private String profile;
	
	@Valid
	private AddressDto address;

	public PeopleDto() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getRegistry() {
		return registry;
	}

	public void setRegistry(String registry) {
		this.registry = registry;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(AddressDto address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "PeopleDto [id=" + id + ", name=" + name + ", email=" + email + ", cpf=" + cpf + ", registry=" + registry
				+ ", rg=" + rg + ", profile=" + profile + ", address=" + address + "]";
	}
}