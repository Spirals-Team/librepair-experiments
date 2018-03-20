package com.abgi.pontointeligente.api.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordUtils {

	/**
	 * Gera um hash utilizando Bcrypt
	 * 
	 * @param senha
	 * @return String
	 */
	public static String gerarBCrypt(String senha) {
		if (senha == null) {
			return senha;
		}
		log.info("Gerando hash com o BCrypt");
		BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
		return bCryptEncoder.encode(senha);
	}
}
