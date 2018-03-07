package com.tiagogouvea.api.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaUtils {
	
	public static String gerarBCrypt(String senha) {
		
		if (senha == null) {
			return senha;
		}
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		return passwordEncoder.encode(senha);
	}
	
	
	public static boolean validaSenha(String senha, String senhaEncoded) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		return passwordEncoder.matches(senha, senhaEncoded);
	}
	

}
