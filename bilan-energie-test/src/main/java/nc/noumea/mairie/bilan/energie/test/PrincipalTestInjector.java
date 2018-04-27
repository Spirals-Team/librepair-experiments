package nc.noumea.mairie.bilan.energie.test;

import java.security.Principal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Injecteur de Principal pour les tests unitaires
 *
 * @author Greg Dujardin
 *
 */
@Configuration
public class PrincipalTestInjector {

	/**
	 * @return Retourne le principal de la session
	 */
	@Scope
	@Bean
	public Principal principal() {
		return new PrincipalTest();
	}

}