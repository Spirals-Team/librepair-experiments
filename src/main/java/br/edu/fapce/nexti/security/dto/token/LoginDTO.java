package br.edu.fapce.nexti.security.dto.token;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

	@NotEmpty(message = "o campo username não pode ser vazio.")
	private String username;
	@NotEmpty(message = "o campo password não pode ser vazio.")
	private String password;

}
