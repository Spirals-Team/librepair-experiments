package com.jairosousa.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * Classe só utilizada para o fogot de senha
 * 
 * @author Jairo
 *
 */
public class EmailDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento Obrigatório")
	@Email(message = "Email inválido")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
