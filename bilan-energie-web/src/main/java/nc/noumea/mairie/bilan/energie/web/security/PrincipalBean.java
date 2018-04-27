package nc.noumea.mairie.bilan.energie.web.security;

import java.security.Principal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Principal contenant 
 * @author David ALEXIS
 * 
 */
@Configuration
public class PrincipalBean {

	/**
	 * @return Retourne le principal de la session
	 */
	@Scope
	@Bean
	public Principal principal() {
		return new BilanPrincipal();
	}

}