package br.com.rodolfo.pontointeligente.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Passwordutils
 */
public class Passwordutils {

    private static final Logger log = LoggerFactory.getLogger(Passwordutils.class);

    public Passwordutils() {}
    
    public static String gerarBCrypt(String senha) {

        if(senha == null) {

            return senha;
        }

        log.info("Gerando hash com o BCrypt.");

        BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();

        return bCryptEncoder.encode(senha);
    }


}