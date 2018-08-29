package br.edu.fapce.nexti.security.dto.loginuser;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.edu.fapce.nexti.security.model.UserRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDTO {

	@NotEmpty(message = "O atributo [loginUserEmail] do loginUser nao pode ser vazio")
	private String loginUserEmail;
	@NotEmpty(message = "O atributo [loginUserPassword] do loginUser nao pode ser vazio")
	private String loginUserPassword;
	@NotNull(message = "O atributo [loginUserRole] do loginUser nao pode ser vazio")
	private UserRole loginUserRole;

	public void setLoginUserEmail(String loginUserEmail) {
		this.loginUserEmail = loginUserEmail.toLowerCase();
	}
	
}
