package br.edu.fapce.nexti.security.dto.loginuser;

import br.edu.fapce.nexti.security.model.LoginUser;
import br.edu.fapce.nexti.security.model.UserRole;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ResponseLoginUserDTO {
	private @NonNull Long id;
	private @NonNull String email;
	private @NonNull UserRole userRole;

	public ResponseLoginUserDTO(LoginUser lu) {
		setId(lu.getId());
		setEmail(lu.getEmail());
		setUserRole(lu.getUserRole());
	}
}
