package br.com.tsp.api.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaUtils {

	/**
	 * Gera um hash utilizando o Bcrypt
	 * 
	 * @param senha
	 * @return String
	 */
	public static String gerarBcrypt(String senha) {
		if (senha == null) {
			return senha;
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
	
	/**
	 * Verifica se a senha é válida
	 * 
	 * @param senha
	 * @param senhaEncoded
	 * @return	boolean
	 */
	public static boolean isSenhaValida(String senha, String senhaEncoded) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(senha, senhaEncoded);
	}
}
